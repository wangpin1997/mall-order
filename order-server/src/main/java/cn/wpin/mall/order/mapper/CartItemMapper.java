package cn.wpin.mall.order.mapper;


import cn.wpin.mall.order.entity.CartItem;
import cn.wpin.mall.order.example.CartItemExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartItemMapper {
    long countByExample(CartItemExample example);

    int deleteByExample(CartItemExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CartItem record);

    int insertSelective(CartItem record);

    List<CartItem> selectByExample(CartItemExample example);

    CartItem selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CartItem record, @Param("example") CartItemExample example);

    int updateByExample(@Param("record") CartItem record, @Param("example") CartItemExample example);

    int updateByPrimaryKeySelective(CartItem record);

    int updateByPrimaryKey(CartItem record);
}