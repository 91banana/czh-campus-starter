package com.campus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("courses")
public class Course {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long semesterId;
    private String courseName;
    private String courseCode;
    private Integer totalHours;
    private java.math.BigDecimal credits;
    private String examType;
    private java.time.LocalDateTime createTime;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getSemesterId() { return semesterId; }
    public void setSemesterId(Long semesterId) { this.semesterId = semesterId; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
    public Integer getTotalHours() { return totalHours; }
    public void setTotalHours(Integer totalHours) { this.totalHours = totalHours; }
    public java.math.BigDecimal getCredits() { return credits; }
    public void setCredits(java.math.BigDecimal credits) { this.credits = credits; }
    public String getExamType() { return examType; }
    public void setExamType(String examType) { this.examType = examType; }
    public java.time.LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(java.time.LocalDateTime createTime) { this.createTime = createTime; }
}
