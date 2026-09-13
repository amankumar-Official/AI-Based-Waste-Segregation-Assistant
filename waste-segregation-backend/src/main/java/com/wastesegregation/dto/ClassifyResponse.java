package com.wastesegregation.dto;

public class ClassifyResponse {
    private String category;
    private String disposalInstructions;
    private String reasoning;
    private String sustainabilityTip;

    // Getters and setters
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getDisposalInstructions() { return disposalInstructions; }
    public void setDisposalInstructions(String d) { this.disposalInstructions = d; }
    public String getReasoning() { return reasoning; }
    public void setReasoning(String reasoning) { this.reasoning = reasoning; }
    public String getSustainabilityTip() { return sustainabilityTip; }
    public void setSustainabilityTip(String tip) { this.sustainabilityTip = tip; }
}
