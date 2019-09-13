package cn.wpin.mall.order.mapper;


import cn.wpin.mall.order.entity.OrderReturnReason;
import cn.wpin.mall.order.example.OrderReturnReasonExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderReturnReasonMapper {
    
    long countByExample(OrderReturnReasonExample example);

    int deleteByExample(OrderReturnReasonExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OrderReturnReason record);

    int insertSelective(OrderReturnReason record);

    List<OrderReturnReason> selectByExample(OrderReturnReasonExample example);

    OrderReturnReason selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OrderReturnReason record, @Param("example") OrderReturnReasonExample example);

    int updateByExample(@Param("record") OrderReturnReason record, @Param("example") OrderReturnReasonExample example);

    int updateByPrimaryKeySelective(OrderReturnReason record);

    int updateByPrimaryKey(OrderReturnReason record);
}