package cn.wpin.mall.order.mapper;


import cn.wpin.mall.order.entity.OrderSetting;
import cn.wpin.mall.order.example.OrderSettingExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderSettingMapper {
    
    long countByExample(OrderSettingExample example);

    int deleteByExample(OrderSettingExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OrderSetting record);

    int insertSelective(OrderSetting record);

    List<OrderSetting> selectByExample(OrderSettingExample example);

    OrderSetting selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OrderSetting record, @Param("example") OrderSettingExample example);

    int updateByExample(@Param("record") OrderSetting record, @Param("example") OrderSettingExample example);

    int updateByPrimaryKeySelective(OrderSetting record);

    int updateByPrimaryKey(OrderSetting record);
}