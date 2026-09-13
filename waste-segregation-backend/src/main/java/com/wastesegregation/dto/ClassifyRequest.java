package com.wastesegregation.dto;

public class ClassifyRequest {
    private String item;
    private String imageBase64;

    public String getItem() { return item; }
    public void setItem(String item) { this.item = item; }

    public String getImageBase64() { return imageBase64; }
    public void setImageBase64(String imageBase64) { this.imageBase64 = imageBase64; }
}
