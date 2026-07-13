package com.campus.controller;

import com.campus.common.Result;
import com.campus.dto.ScheduleVO;
import com.campus.dto.SemesterVO;
import com.campus.service.AdminService;
import com.campus.service.ScheduleService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private AdminService adminService;

    @GetMapping("/my")
    public Result<List<ScheduleVO>> getMySchedule(HttpServletRequest request,
                                                   @RequestParam(required = false) Long semesterId) {
        Long userId = (Long) request.getAttribute("userId");
        List<ScheduleVO> list = scheduleService.getMySchedule(userId, semesterId);
        return Result.ok(list);
    }

    @GetMapping("/current-semester")
    public Result<SemesterVO> getCurrentSemester() {
        List<SemesterVO> list = adminService.getSemesters();
        for (SemesterVO vo : list) {
            if (vo.getIsCurrent() != null && vo.getIsCurrent() == 1) {
                return Result.ok(vo);
            }
        }
        if (!list.isEmpty()) {
            return Result.ok(list.get(0));
        }
        return Result.ok(null);
    }
}
