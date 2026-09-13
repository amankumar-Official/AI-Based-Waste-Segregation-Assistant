package com.wastesegregation.controller;

import com.wastesegregation.dto.ClassifyRequest;
import com.wastesegregation.dto.ClassifyResponse;
import com.wastesegregation.service.WasteAiService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/waste")
@CrossOrigin(origins = "http://localhost:4200")
public class WasteController {

    private final WasteAiService aiService;

    public WasteController(WasteAiService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/classify")
    public ClassifyResponse classify(@RequestBody ClassifyRequest request) {
        if (request.getItem() != null && request.getItem().length() > 100) {
            throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "Item description is too long. Maximum allowed length is 100 characters.");
        }
        return aiService.classify(request);
    }
}
