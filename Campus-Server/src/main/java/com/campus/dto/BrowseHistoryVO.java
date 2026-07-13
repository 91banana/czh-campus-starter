package com.campus.dto;

public class BrowseHistoryVO {
    private Long id;
    private Long productId;
    private String title;
    private Double price;
    private String originalPrice;
    private String images;
    private String categoryName;
    private String campusLocation;
    private Integer conditionLevel;
    private String browseTime;
    private Integer sellerId;
    private String sellerName;
    private Integer status;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public String getOriginalPrice() { return originalPrice; }
    public void setOriginalPrice(String originalPrice) { this.originalPrice = originalPrice; }
    public String getImages() { return images; }
    public void setImages(String images) { this.images = images; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public String getCampusLocation() { return campusLocation; }
    public void setCampusLocation(String campusLocation) { this.campusLocation = campusLocation; }
    public Integer getConditionLevel() { return conditionLevel; }
    public void setConditionLevel(Integer conditionLevel) { this.conditionLevel = conditionLevel; }
    public String getBrowseTime() { return browseTime; }
    public void setBrowseTime(String browseTime) { this.browseTime = browseTime; }
    public Integer getSellerId() { return sellerId; }
    public void setSellerId(Integer sellerId) { this.sellerId = sellerId; }
    public String getSellerName() { return sellerName; }
    public void setSellerName(String sellerName) { this.sellerName = sellerName; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
}
