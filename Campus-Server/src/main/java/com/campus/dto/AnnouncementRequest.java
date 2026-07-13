package com.campus.dto;

import jakarta.validation.constraints.NotBlank;

public class AnnouncementRequest {
    @NotBlank(message = "标题不能为空")
    private String title;
    @NotBlank(message = "内容不能为空")
    private String content;
    private String summary;
    @NotBlank(message = "分类不能为空")
    private String category;
    private Integer isTop;
    private Integer isPublished;
    private String targetTags;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public Integer getIsTop() { return isTop; }
    public void setIsTop(Integer isTop) { this.isTop = isTop; }
    public Integer getIsPublished() { return isPublished; }
    public void setIsPublished(Integer isPublished) { this.isPublished = isPublished; }
    public String getTargetTags() { return targetTags; }
    public void setTargetTags(String targetTags) { this.targetTags = targetTags; }
}
