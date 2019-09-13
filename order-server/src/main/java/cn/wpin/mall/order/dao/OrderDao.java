package cn.wpin.mall.order.dao;

import cn.wpin.mall.order.dto.OrderDeliveryParam;
import cn.wpin.mall.order.dto.OrderDetail;
import cn.wpin.mall.order.dto.OrderQueryParam;
import cn.wpin.mall.order.entity.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单自定义查询Dao
 * @author wangpin 
 */
public interface OrderDao {
    /**
     * 条件查询订单
     */
    List<Order> getList(@Param("queryParam") OrderQueryParam queryParam);

    /**
     * 批量发货
     */
    int delivery(@Param("list") List<OrderDeliveryParam> deliveryParamList);

    /**
     * 获取订单详情
     */
    OrderDetail getDetail(@Param("id") Long id);
}
