package com.campus.service;

import com.campus.entity.Favorite;
import com.campus.mapper.FavoriteMapper;
import com.campus.dto.FavoriteVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteMapper favoriteMapper;

    @Autowired
    private com.campus.mapper.ProductMapper productMapper;

    public List<FavoriteVO> getMyFavorites(Long userId) {
        List<Favorite> list = favoriteMapper.selectByUserId(userId);
        List<FavoriteVO> result = new ArrayList<>();
        for (Favorite f : list) {
            FavoriteVO vo = new FavoriteVO();
            vo.setId(f.getId());
            vo.setProductId(f.getProductId());
            vo.setProductTitle(f.getProductTitle());
            vo.setProductPrice(f.getProductPrice());
            vo.setProductImages(f.getProductImages());
            vo.setProductStatus(f.getProductStatus());
            vo.setSellerName(f.getSellerName());
            vo.setCreateTime(f.getCreateTime() != null ? f.getCreateTime().toString() : "");
            result.add(vo);
        }
        return result;
    }

    public void addFavorite(Long userId, Long productId) {
        int count = favoriteMapper.countByUserAndProduct(userId, productId);
        if (count == 0) {
            Favorite f = new Favorite();
            f.setUserId(userId);
            f.setProductId(productId);
            favoriteMapper.insert(f);
            com.campus.entity.Product product = productMapper.selectById(productId);
            if (product != null) {
                product.setFavoriteCount(product.getFavoriteCount() != null ? product.getFavoriteCount() + 1 : 1);
                productMapper.updateById(product);
            }
        }
    }

    public void removeFavorite(Long userId, Long productId) {
        int count = favoriteMapper.countByUserAndProduct(userId, productId);
        if (count > 0) {
            favoriteMapper.delete(new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<Favorite>()
                    .eq(Favorite::getUserId, userId).eq(Favorite::getProductId, productId));
            com.campus.entity.Product product = productMapper.selectById(productId);
            if (product != null && product.getFavoriteCount() != null && product.getFavoriteCount() > 0) {
                product.setFavoriteCount(product.getFavoriteCount() - 1);
                productMapper.updateById(product);
            }
        }
    }

    public boolean isFavorite(Long userId, Long productId) {
        return favoriteMapper.countByUserAndProduct(userId, productId) > 0;
    }
}
