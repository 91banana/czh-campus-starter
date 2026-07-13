package com.campus.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.campus.dto.LostFoundVO;
import com.campus.entity.LostFound;
import com.campus.entity.User;
import com.campus.mapper.LostFoundMapper;
import com.campus.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class LostFoundService {

    @Autowired
    private LostFoundMapper lostFoundMapper;

    @Autowired
    private UserMapper userMapper;

    public List<LostFoundVO> getList(Integer type, Integer status, String keyword, int page, int size) {
        QueryWrapper<LostFound> wrapper = new QueryWrapper<>();
        if (type != null) {
            wrapper.eq("type", type);
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.and(w -> w.like("title", keyword).or().like("description", keyword).or().like("location_desc", keyword));
        }
        wrapper.orderByDesc("create_time");
        wrapper.last("LIMIT " + (page - 1) * size + "," + size);
        List<LostFound> list = lostFoundMapper.selectList(wrapper);

        List<LostFoundVO> result = new ArrayList<>();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (LostFound lf : list) {
            LostFoundVO vo = new LostFoundVO();
            vo.setId(lf.getId());
            vo.setUserId(lf.getUserId());
            vo.setType(lf.getType());
            vo.setTitle(lf.getTitle());
            vo.setDescription(lf.getDescription());
            vo.setImages(lf.getImages());
            vo.setLocationDesc(lf.getLocationDesc());
            vo.setCategory(lf.getCategory());
            vo.setContactPhone(lf.getContactPhone());
            vo.setStatus(lf.getStatus());
            if (lf.getCreateTime() != null) {
                vo.setCreateTime(lf.getCreateTime().format(fmt));
            }
            User user = userMapper.selectById(lf.getUserId());
            if (user != null) {
                vo.setUserName(user.getName());
            }
            result.add(vo);
        }
        return result;
    }

    public LostFound publish(Long userId, LostFound lostFound) {
        lostFound.setUserId(userId);
        lostFound.setStatus(1);
        lostFoundMapper.insert(lostFound);
        return lostFound;
    }

    public boolean deleteById(Long id, Long userId) {
        LostFound lf = lostFoundMapper.selectById(id);
        if (lf == null || !lf.getUserId().equals(userId)) {
            return false;
        }
        lostFoundMapper.deleteById(id);
        return true;
    }

    public LostFoundVO getDetail(Long id) {
        LostFound lf = lostFoundMapper.selectById(id);
        if (lf == null) {
            return null;
        }
        LostFoundVO vo = new LostFoundVO();
        vo.setId(lf.getId());
        vo.setUserId(lf.getUserId());
        vo.setType(lf.getType());
        vo.setTitle(lf.getTitle());
        vo.setDescription(lf.getDescription());
        vo.setImages(lf.getImages());
        vo.setLocationDesc(lf.getLocationDesc());
        vo.setCategory(lf.getCategory());
        vo.setContactPhone(lf.getContactPhone());
        vo.setStatus(lf.getStatus());
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        if (lf.getCreateTime() != null) {
            vo.setCreateTime(lf.getCreateTime().format(fmt));
        }
        User user = userMapper.selectById(lf.getUserId());
        if (user != null) {
            vo.setUserName(user.getName());
        }
        return vo;
    }

    public List<LostFoundVO> myList(Long userId) {
        QueryWrapper<LostFound> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.orderByDesc("create_time");
        List<LostFound> list = lostFoundMapper.selectList(wrapper);
        List<LostFoundVO> result = new ArrayList<>();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (LostFound lf : list) {
            LostFoundVO vo = new LostFoundVO();
            vo.setId(lf.getId());
            vo.setUserId(lf.getUserId());
            vo.setType(lf.getType());
            vo.setTitle(lf.getTitle());
            vo.setDescription(lf.getDescription());
            vo.setImages(lf.getImages());
            vo.setLocationDesc(lf.getLocationDesc());
            vo.setCategory(lf.getCategory());
            vo.setContactPhone(lf.getContactPhone());
            vo.setStatus(lf.getStatus());
            if (lf.getCreateTime() != null) {
                vo.setCreateTime(lf.getCreateTime().format(fmt));
            }
            User user = userMapper.selectById(lf.getUserId());
            if (user != null) {
                vo.setUserName(user.getName());
            }
            result.add(vo);
        }
        return result;
    }
}
