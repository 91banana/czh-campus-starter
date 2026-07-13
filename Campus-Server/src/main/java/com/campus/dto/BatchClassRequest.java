package com.campus.dto;

import java.util.List;

public class BatchClassRequest {
    private String grade;
    private List<Long> collegeIds;
    private List<Long> majorIds;
    private Integer classCount;

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
    public List<Long> getCollegeIds() { return collegeIds; }
    public void setCollegeIds(List<Long> collegeIds) { this.collegeIds = collegeIds; }
    public List<Long> getMajorIds() { return majorIds; }
    public void setMajorIds(List<Long> majorIds) { this.majorIds = majorIds; }
    public Integer getClassCount() { return classCount; }
    public void setClassCount(Integer classCount) { this.classCount = classCount; }
}
