package cn.wpin.mall.order.mapper;

import cn.wpin.mall.order.entity.OrderReturnApply;
import cn.wpin.mall.order.example.OrderReturnApplyExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderReturnApplyMapper {

    long countByExample(OrderReturnApplyExample example);

    int deleteByExample(OrderReturnApplyExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OrderReturnApply record);

    int insertSelective(OrderReturnApply record);

    List<OrderReturnApply> selectByExample(OrderReturnApplyExample example);

    OrderReturnApply selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OrderReturnApply record, @Param("example") OrderReturnApplyExample example);

    int updateByExample(@Param("record") OrderReturnApply record, @Param("example") OrderReturnApplyExample example);

    int updateByPrimaryKeySelective(OrderReturnApply record);

    int updateByPrimaryKey(OrderReturnApply record);
}