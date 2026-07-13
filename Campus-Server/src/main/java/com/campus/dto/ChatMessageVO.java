package com.campus.dto;

public class ChatMessageVO {
    private Long id;
    private Long conversationId;
    private Long senderId;
    private String content;
    private String msgType;
    private String createTime;
    private Boolean isMine;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getConversationId() { return conversationId; }
    public void setConversationId(Long conversationId) { this.conversationId = conversationId; }
    public Long getSenderId() { return senderId; }
    public void setSenderId(Long senderId) { this.senderId = senderId; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getMsgType() { return msgType; }
    public void setMsgType(String msgType) { this.msgType = msgType; }
    public String getCreateTime() { return createTime; }
    public void setCreateTime(String createTime) { this.createTime = createTime; }
    public Boolean getIsMine() { return isMine; }
    public void setIsMine(Boolean isMine) { this.isMine = isMine; }
}
