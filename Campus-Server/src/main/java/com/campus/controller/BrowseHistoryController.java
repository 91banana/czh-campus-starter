package com.campus.controller;

import com.campus.common.Result;
import com.campus.dto.BrowseHistoryVO;
import com.campus.service.BrowseHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/browse")
public class BrowseHistoryController {

    @Autowired
    private BrowseHistoryService browseHistoryService;

    @PostMapping("/record")
    public Result<Boolean> record(@RequestAttribute("userId") Long userId, @RequestParam Long productId) {
        browseHistoryService.recordBrowse(userId, productId);
        return Result.ok(true);
    }

    @GetMapping("/my-history")
    public Result<List<BrowseHistoryVO>> myHistory(@RequestAttribute("userId") Long userId) {
        return Result.ok(browseHistoryService.getMyHistory(userId));
    }

    @PostMapping("/clear")
    public Result<Boolean> clear(@RequestAttribute("userId") Long userId) {
        return Result.ok(browseHistoryService.clearHistory(userId));
    }
}
