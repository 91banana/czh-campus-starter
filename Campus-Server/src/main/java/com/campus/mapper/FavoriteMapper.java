package com.campus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.campus.entity.Favorite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FavoriteMapper extends BaseMapper<Favorite> {

    @Select("SELECT f.*, p.title as product_title, p.price as product_price, p.images as product_images, p.status as product_status, u.name as seller_name " +
            "FROM favorites f LEFT JOIN products p ON f.product_id = p.id LEFT JOIN users u ON p.seller_id = u.id " +
            "WHERE f.user_id = #{userId} ORDER BY f.create_time DESC")
    List<Favorite> selectByUserId(Long userId);

    @Select("SELECT COUNT(*) FROM favorites WHERE user_id = #{userId} AND product_id = #{productId}")
    int countByUserAndProduct(Long userId, Long productId);
}
