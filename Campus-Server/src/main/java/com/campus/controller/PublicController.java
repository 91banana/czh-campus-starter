package com.campus.controller;

import com.campus.common.Result;
import com.campus.entity.Clazz;
import com.campus.entity.College;
import com.campus.entity.Major;
import com.campus.entity.ProductCategory;
import com.campus.service.AdminService;
import com.campus.mapper.ProductCategoryMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public")
public class PublicController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @GetMapping("/colleges")
    public Result<List<College>> getColleges() {
        return Result.ok(adminService.getColleges());
    }

    @GetMapping("/majors")
    public Result<List<Major>> getMajors(@RequestParam(required = false) Long collegeId) {
        return Result.ok(adminService.getMajors(collegeId));
    }

    @GetMapping("/classes")
    public Result<List<com.campus.dto.ClassVO>> getClasses(@RequestParam(required = false) String grade,
                                                            @RequestParam(required = false) Long collegeId,
                                                            @RequestParam(required = false) Long majorId) {
        return Result.ok(adminService.getClasses(grade, collegeId, majorId));
    }

    @GetMapping("/grades")
    public Result<List<String>> getGrades() {
        return Result.ok(adminService.getGrades());
    }

    @GetMapping("/product-categories")
    public Result<List<ProductCategory>> getProductCategories() {
        List<ProductCategory> list = productCategoryMapper.selectList(
                new LambdaQueryWrapper<ProductCategory>().orderByAsc(ProductCategory::getSortOrder));
        return Result.ok(list);
    }
}
