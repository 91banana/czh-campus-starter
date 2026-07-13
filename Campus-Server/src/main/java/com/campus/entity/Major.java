package com.campus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("majors")
public class Major {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long collegeId;
    private String name;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getCollegeId() { return collegeId; }
    public void setCollegeId(Long collegeId) { this.collegeId = collegeId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
