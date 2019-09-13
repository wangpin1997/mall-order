package cn.wpin.mall.order.dao;

import cn.wpin.mall.order.dto.OrderReturnApplyResult;
import cn.wpin.mall.order.dto.ReturnApplyQueryParam;
import cn.wpin.mall.order.entity.OrderReturnApply;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单退货申请自定义Dao
 * @author wangpin 
 */
public interface OrderReturnApplyDao {
    /**
     * 查询申请列表
     */
    List<OrderReturnApply> getList(@Param("queryParam") ReturnApplyQueryParam queryParam);

    /**
     * 获取申请详情
     */
    OrderReturnApplyResult getDetail(@Param("id") Long id);
}
