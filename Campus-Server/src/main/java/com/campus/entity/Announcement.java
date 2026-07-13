package com.campus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("announcements")
public class Announcement {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private String content;
    private String summary;
    private String category;
    private Long publisherId;
    private Integer isTop;
    private Integer isPublished;
    private java.time.LocalDateTime publishTime;
    private java.time.LocalDateTime expireTime;
    private String targetTags;
    private java.time.LocalDateTime createTime;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public Long getPublisherId() { return publisherId; }
    public void setPublisherId(Long publisherId) { this.publisherId = publisherId; }
    public Integer getIsTop() { return isTop; }
    public void setIsTop(Integer isTop) { this.isTop = isTop; }
    public Integer getIsPublished() { return isPublished; }
    public void setIsPublished(Integer isPublished) { this.isPublished = isPublished; }
    public java.time.LocalDateTime getPublishTime() { return publishTime; }
    public void setPublishTime(java.time.LocalDateTime publishTime) { this.publishTime = publishTime; }
    public java.time.LocalDateTime getExpireTime() { return expireTime; }
    public void setExpireTime(java.time.LocalDateTime expireTime) { this.expireTime = expireTime; }
    public String getTargetTags() { return targetTags; }
    public void setTargetTags(String targetTags) { this.targetTags = targetTags; }
    public java.time.LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(java.time.LocalDateTime createTime) { this.createTime = createTime; }
}
