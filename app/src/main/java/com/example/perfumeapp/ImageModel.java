package com.example.perfumeapp;
public class ImageModel {
    private String imageUrl;
    private String description;

    public ImageModel() {
        // Default constructor required for calls to DataSnapshot.getValue(ImageModel.class)
    }

    public ImageModel(String imageUrl, String description) {
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
