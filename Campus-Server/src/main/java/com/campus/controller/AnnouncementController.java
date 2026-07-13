package com.campus.controller;

import com.campus.common.Result;
import com.campus.entity.Announcement;
import com.campus.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/announcement")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @GetMapping("/list")
    public Result<List<Announcement>> getList(@RequestParam(required = false) String category) {
        List<Announcement> list = announcementService.getList(category);
        return Result.ok(list);
    }
}
