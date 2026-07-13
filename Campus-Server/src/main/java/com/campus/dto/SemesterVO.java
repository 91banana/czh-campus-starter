package com.campus.dto;

public class SemesterVO {
    private Long id;
    private String name;
    private String startDate;
    private String endDate;
    private Integer weekCount;
    private Integer isCurrent;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }
    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }
    public Integer getWeekCount() { return weekCount; }
    public void setWeekCount(Integer weekCount) { this.weekCount = weekCount; }
    public Integer getIsCurrent() { return isCurrent; }
    public void setIsCurrent(Integer isCurrent) { this.isCurrent = isCurrent; }
}
