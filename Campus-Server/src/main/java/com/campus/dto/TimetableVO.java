package com.campus.dto;

public class TimetableVO {
    private Long id;
    private String courseName;
    private String teacher;
    private String classroom;
    private Integer dayOfWeek;
    private Integer startWeek;
    private Integer endWeek;
    private Integer startSection;
    private Integer endSection;
    private String color;
    private String remark;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public String getTeacher() { return teacher; }
    public void setTeacher(String teacher) { this.teacher = teacher; }
    public String getClassroom() { return classroom; }
    public void setClassroom(String classroom) { this.classroom = classroom; }
    public Integer getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(Integer dayOfWeek) { this.dayOfWeek = dayOfWeek; }
    public Integer getStartWeek() { return startWeek; }
    public void setStartWeek(Integer startWeek) { this.startWeek = startWeek; }
    public Integer getEndWeek() { return endWeek; }
    public void setEndWeek(Integer endWeek) { this.endWeek = endWeek; }
    public Integer getStartSection() { return startSection; }
    public void setStartSection(Integer startSection) { this.startSection = startSection; }
    public Integer getEndSection() { return endSection; }
    public void setEndSection(Integer endSection) { this.endSection = endSection; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
}
