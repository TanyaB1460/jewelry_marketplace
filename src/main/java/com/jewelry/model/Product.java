package com.jewelry.model;

public class Product {
    private Integer id;
    private String title;
    private String description;
    private Double price;
    private Integer makerId;
    private String category;
    private Boolean isActive;
    private String makerUsername;

    public Product() {}
    public Product(Integer id, String title, String description, Double price,
                   Integer makerId, String category, Boolean isActive, String makerUsername) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.makerId = makerId;
        this.category = category;
        this.isActive = isActive;
        this.makerUsername = makerUsername;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public Integer getMakerId() { return makerId; }
    public void setMakerId(Integer makerId) { this.makerId = makerId; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    public String getMakerUsername() { return makerUsername; }
    public void setMakerUsername(String makerUsername) { this.makerUsername = makerUsername; }
}
