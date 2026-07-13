package com.campus.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.campus.dto.BrowseHistoryVO;
import com.campus.entity.BrowseHistory;
import com.campus.entity.Product;
import com.campus.entity.User;
import com.campus.mapper.BrowseHistoryMapper;
import com.campus.mapper.ProductMapper;
import com.campus.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class BrowseHistoryService {

    @Autowired
    private BrowseHistoryMapper browseHistoryMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private UserMapper userMapper;

    public void recordBrowse(Long userId, Long productId) {
        LambdaQueryWrapper<BrowseHistory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BrowseHistory::getUserId, userId)
               .eq(BrowseHistory::getProductId, productId);
        BrowseHistory existing = browseHistoryMapper.selectOne(wrapper);
        if (existing != null) {
            existing.setBrowseTime(LocalDateTime.now());
            browseHistoryMapper.updateById(existing);
        } else {
            BrowseHistory bh = new BrowseHistory();
            bh.setUserId(userId);
            bh.setProductId(productId);
            bh.setBrowseTime(LocalDateTime.now());
            browseHistoryMapper.insert(bh);
        }
    }

    public List<BrowseHistoryVO> getMyHistory(Long userId) {
        LambdaQueryWrapper<BrowseHistory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BrowseHistory::getUserId, userId)
               .orderByDesc(BrowseHistory::getBrowseTime)
               .last("LIMIT 50");
        List<BrowseHistory> list = browseHistoryMapper.selectList(wrapper);

        List<BrowseHistoryVO> result = new ArrayList<>();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (BrowseHistory bh : list) {
            Product p = productMapper.selectById(bh.getProductId());
            if (p == null) continue;
            BrowseHistoryVO vo = new BrowseHistoryVO();
            vo.setId(bh.getId());
            vo.setProductId(p.getId());
            vo.setTitle(p.getTitle());
            vo.setPrice(p.getPrice() != null ? p.getPrice().doubleValue() : 0.0);
            vo.setOriginalPrice(p.getOriginalPrice() != null && p.getOriginalPrice().doubleValue() > 0 ? p.getOriginalPrice().toPlainString() : null);
            vo.setImages(p.getImages());
            vo.setCampusLocation(p.getCampusLocation());
            vo.setConditionLevel(p.getConditionLevel());
            vo.setStatus(p.getStatus());
            vo.setBrowseTime(bh.getBrowseTime() != null ? bh.getBrowseTime().format(fmt) : "");
            User seller = userMapper.selectById(p.getSellerId());
            if (seller != null) {
                vo.setSellerId(seller.getId().intValue());
                vo.setSellerName(seller.getName());
            }
            result.add(vo);
        }
        return result;
    }

    public boolean clearHistory(Long userId) {
        LambdaQueryWrapper<BrowseHistory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BrowseHistory::getUserId, userId);
        browseHistoryMapper.delete(wrapper);
        return true;
    }
}
