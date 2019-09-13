package cn.wpin.mall.order.dao;

import cn.wpin.mall.order.entity.OrderOperateHistory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单操作记录自定义Dao
 * @author wangpin
 */
public interface OrderOperateHistoryDao {
    int insertList(@Param("list") List<OrderOperateHistory> orderOperateHistoryList);
}
