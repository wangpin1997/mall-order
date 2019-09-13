package cn.wpin.mall.order.dto;

import cn.wpin.mall.order.entity.Order;
import cn.wpin.mall.order.entity.OrderItem;
import cn.wpin.mall.order.entity.OrderOperateHistory;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 订单详情信息
 * @author wangpin
 */
public class OrderDetail extends Order {

    @Getter
    @Setter
    private List<OrderItem> orderItemList;
    @Getter
    @Setter
    private List<OrderOperateHistory> historyList;
}
