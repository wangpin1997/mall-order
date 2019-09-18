package cn.wpin.mall.order.dao;

import cn.wpin.mall.order.entity.OrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单商品信息自定义Dao
 * @author wangpin
 */
public interface PortalOrderItemDao {
    int insertList(@Param("list") List<OrderItem> list);
}
