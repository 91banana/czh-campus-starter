package com.campus.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.campus.entity.Announcement;
import com.campus.mapper.AnnouncementMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnouncementService {

    @Autowired
    private AnnouncementMapper announcementMapper;

    public List<Announcement> getList(String category) {
        LambdaQueryWrapper<Announcement> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Announcement::getIsPublished, 1);
        if (category != null && !category.isEmpty() && !"全部".equals(category)) {
            wrapper.eq(Announcement::getCategory, category);
        }
        wrapper.orderByDesc(Announcement::getIsTop, Announcement::getPublishTime);
        return announcementMapper.selectList(wrapper);
    }
}
