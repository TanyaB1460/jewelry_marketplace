package com.jewelry.model;

public class Order {
    private Integer id;
    private Integer customerId;
    private Integer productId;
    private Integer quantity;
    private String status;
    private String productTitle;
    private Double productPrice;
    private String customerUsername;

    public Order() {}
    public Order(Integer id, Integer customerId, Integer productId, Integer quantity,
                 String status, String productTitle, Double productPrice, String customerUsername) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
        this.status = status;
        this.productTitle = productTitle;
        this.productPrice = productPrice;
        this.customerUsername = customerUsername;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }
    public Integer getProductId() { return productId; }
    public void setProductId(Integer productId) { this.productId = productId; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getProductTitle() { return productTitle; }
    public void setProductTitle(String productTitle) { this.productTitle = productTitle; }
    public Double getProductPrice() { return productPrice; }
    public void setProductPrice(Double productPrice) { this.productPrice = productPrice; }
    public String getCustomerUsername() { return customerUsername; }
    public void setCustomerUsername(String customerUsername) { this.customerUsername = customerUsername; }
}
