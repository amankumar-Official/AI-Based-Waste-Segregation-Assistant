package com.wastesegregation.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wastesegregation.dto.ClassifyResponse;
import com.wastesegregation.model.WasteQuery;
import com.wastesegregation.repository.WasteQueryRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class WasteAiService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final WasteQueryRepository repository;

    @Value("${ai.api.url}")
    private String apiUrl;

    @Value("${ai.api.key}")
    private String apiKey;

    @Value("${ai.api.model}")
    private String model;

    public WasteAiService(WasteQueryRepository repository) {
        this.repository = repository;
    }

    public ClassifyResponse classify(com.wastesegregation.dto.ClassifyRequest classifyRequest) {
        String item = classifyRequest.getItem() != null && !classifyRequest.getItem().trim().isEmpty() ? classifyRequest.getItem() : "Image";
        String prompt = buildPrompt(item);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Object content;
        if (classifyRequest.getImageBase64() != null && !classifyRequest.getImageBase64().isEmpty()) {
            content = new Object[]{
                Map.of("type", "text", "text", prompt),
                Map.of("type", "image_url", "image_url", Map.of("url", classifyRequest.getImageBase64()))
            };
        } else {
            content = prompt;
        }

        Map<String, Object> body = Map.of(
            "model", model,
            "messages", new Object[]{
                Map.of("role", "user", "content", content)
            },
            "temperature", 0.2
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        Map<?, ?> apiResponse = restTemplate.postForObject(apiUrl, request, Map.class);

        String rawContent = extractContent(apiResponse);

        ClassifyResponse result;
        try {
            result = objectMapper.readValue(rawContent, ClassifyResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse AI response: " + rawContent, e);
        }

        saveHistory(item, result);
        return result;
    }

    private String buildPrompt(String item) {
        return """
            You are an expert waste management and recycling assistant with deep
            knowledge of environmental sustainability, SDG 12 (Responsible Consumption
            and Production), and general municipal waste segregation practices.

            Your task: Given the name or description of a waste item, classify it
            into EXACTLY ONE of these categories:
            - RECYCLABLE (dry waste: plastic, paper, metal, glass, cardboard)
            - ORGANIC (wet/biodegradable waste: food scraps, garden waste)
            - E_WASTE (electronics, batteries, bulbs, cables, chargers)
            - HAZARDOUS (chemicals, medical waste, sharp objects, paint)
            - LANDFILL (items that cannot currently be recycled or composted)

            Item to classify: "%s"

            Respond ONLY with a single valid JSON object in exactly this shape, with
            no text before or after it, and no markdown code fences:

            {
              "category": "<one of RECYCLABLE, ORGANIC, E_WASTE, HAZARDOUS, LANDFILL>",
              "disposalInstructions": "<2-3 sentence practical instructions>",
              "reasoning": "<1-2 sentence explanation>",
              "sustainabilityTip": "<1 short actionable tip>"
            }

            Rules:
            1. Always choose exactly one category.
            2. If uncertain, prefer HAZARDOUS over LANDFILL when there is doubt about safety.
            3. Use plain, simple language.
            4. Do not include opinions or text outside the JSON object.
            5. Do not wrap the JSON in backticks.
            """.formatted(item);
    }

    @SuppressWarnings("unchecked")
    private String extractContent(Map<?, ?> apiResponse) {
        var choices = (java.util.List<Map<String, Object>>) apiResponse.get("choices");
        var message = (Map<String, Object>) choices.get(0).get("message");
        return (String) message.get("content");
    }

    private void saveHistory(String item, ClassifyResponse result) {
        WasteQuery query = new WasteQuery();
        query.setItem(item);
        query.setCategory(result.getCategory());
        query.setDisposalInstructions(result.getDisposalInstructions());
        query.setReasoning(result.getReasoning());
        query.setQueriedAt(LocalDateTime.now());
        repository.save(query);
    }
}
