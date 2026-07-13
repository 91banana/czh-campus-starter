package com.campus.dto;

public class ConversationVO {
    private Long id;
    private Long otherUserId;
    private String otherUserName;
    private String lastMessage;
    private String lastTime;
    private Integer unreadCount;
    private Long productId;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getOtherUserId() { return otherUserId; }
    public void setOtherUserId(Long otherUserId) { this.otherUserId = otherUserId; }
    public String getOtherUserName() { return otherUserName; }
    public void setOtherUserName(String otherUserName) { this.otherUserName = otherUserName; }
    public String getLastMessage() { return lastMessage; }
    public void setLastMessage(String lastMessage) { this.lastMessage = lastMessage; }
    public String getLastTime() { return lastTime; }
    public void setLastTime(String lastTime) { this.lastTime = lastTime; }
    public Integer getUnreadCount() { return unreadCount; }
    public void setUnreadCount(Integer unreadCount) { this.unreadCount = unreadCount; }
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
}
