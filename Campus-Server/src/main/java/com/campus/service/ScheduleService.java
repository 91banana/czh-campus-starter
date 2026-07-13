package com.campus.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.campus.dto.ScheduleVO;
import com.campus.entity.ClassTimetable;
import com.campus.entity.User;
import com.campus.mapper.ClassTimetableMapper;
import com.campus.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    private ClassTimetableMapper classTimetableMapper;

    @Autowired
    private UserMapper userMapper;

    public List<ScheduleVO> getMySchedule(Long userId, Long semesterId) {
        if (semesterId == null) {
            semesterId = 1L;
        }
        User user = userMapper.selectById(userId);
        if (user == null || user.getClassId() == null) {
            return new ArrayList<>();
        }
        List<ClassTimetable> list = classTimetableMapper.selectList(
                new LambdaQueryWrapper<ClassTimetable>()
                        .eq(ClassTimetable::getClassId, user.getClassId())
                        .eq(ClassTimetable::getSemesterId, semesterId)
                        .orderByAsc(ClassTimetable::getDayOfWeek, ClassTimetable::getStartSection));
        List<ScheduleVO> result = new ArrayList<>();
        for (ClassTimetable ct : list) {
            ScheduleVO vo = new ScheduleVO();
            vo.setId(ct.getId());
            vo.setCourseName(ct.getCourseName());
            vo.setClassroom(ct.getClassroom());
            vo.setDayOfWeek(ct.getDayOfWeek());
            vo.setStartSection(ct.getStartSection());
            vo.setEndSection(ct.getEndSection());
            vo.setStartWeek(ct.getStartWeek());
            vo.setEndWeek(ct.getEndWeek());
            vo.setTeacherName(ct.getTeacher());
            result.add(vo);
        }
        return result;
    }
}
