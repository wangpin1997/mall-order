package cn.wpin.mall.order.mapper;

import cn.wpin.mall.order.entity.OrderOperateHistory;
import cn.wpin.mall.order.example.OrderOperateHistoryExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderOperateHistoryMapper {
    
    long countByExample(OrderOperateHistoryExample example);

    int deleteByExample(OrderOperateHistoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OrderOperateHistory record);

    int insertSelective(OrderOperateHistory record);

    List<OrderOperateHistory> selectByExample(OrderOperateHistoryExample example);

    OrderOperateHistory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OrderOperateHistory record, @Param("example") OrderOperateHistoryExample example);

    int updateByExample(@Param("record") OrderOperateHistory record, @Param("example") OrderOperateHistoryExample example);

    int updateByPrimaryKeySelective(OrderOperateHistory record);

    int updateByPrimaryKey(OrderOperateHistory record);
}