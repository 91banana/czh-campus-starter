package com.campus.controller;

import com.campus.common.Result;
import com.campus.dto.LostFoundVO;
import com.campus.entity.LostFound;
import com.campus.service.LostFoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lost-found")
public class LostFoundController {

    @Autowired
    private LostFoundService lostFoundService;

    @Autowired
    private com.campus.service.ComplaintService complaintService;

    @GetMapping("/list")
    public Result<List<LostFoundVO>> getList(
            @RequestParam(required = false) Integer type,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        return Result.ok(lostFoundService.getList(type, status, keyword, page, size));
    }

    @GetMapping("/detail")
    public Result<LostFoundVO> getDetail(@RequestParam Long id) {
        LostFoundVO vo = lostFoundService.getDetail(id);
        if (vo == null) {
            return Result.error(404, "未找到");
        }
        return Result.ok(vo);
    }

    @PostMapping("/publish")
    public Result<LostFound> publish(@RequestAttribute("userId") Long userId, @RequestBody LostFound lostFound) {
        if (!complaintService.canTrade(userId)) {
            return Result.error(403, "信用分不足85，无法发布");
        }
        if (lostFound.getContactPhone() == null || lostFound.getContactPhone().isEmpty()) {
            return Result.error(400, "请填写联系电话");
        }
        return Result.ok(lostFoundService.publish(userId, lostFound));
    }

    @GetMapping("/my-list")
    public Result<List<LostFoundVO>> myList(@RequestAttribute("userId") Long userId) {
        return Result.ok(lostFoundService.myList(userId));
    }

    @PostMapping("/delete")
    public Result<Boolean> delete(@RequestAttribute("userId") Long userId, @RequestParam Long id) {
        return Result.ok(lostFoundService.deleteById(id, userId));
    }
}
