package cn.wpin.mall.order.service;

import cn.wpin.mall.order.entity.OrderSetting;
import cn.wpin.mall.order.mapper.OrderSettingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 订单设置Service
 * @author wangpin
 */
@Service
public class OrderSettingService {

    @Autowired
    private OrderSettingMapper orderSettingMapper;

    /**
     * 获取指定订单设置
     */
    public OrderSetting getItem(Long id) {
        return orderSettingMapper.selectByPrimaryKey(id);
    }


    /**
     * 修改指定订单设置
     */
    public int update(Long id, OrderSetting orderSetting) {
        orderSetting.setId(id);
        return orderSettingMapper.updateByPrimaryKey(orderSetting);
    }
    
    
}
