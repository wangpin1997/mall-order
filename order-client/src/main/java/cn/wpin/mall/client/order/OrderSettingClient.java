package cn.wpin.mall.client.order;

import cn.wpin.mall.order.entity.OrderSetting;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author wangpin
 */
@FeignClient("order")
public interface OrderSettingClient {

    /**
     * 获取指定订单设置
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "orderSetting/{id}", method = RequestMethod.GET)
    OrderSetting getItem(@PathVariable Long id);

    /**
     * 修改指定订单设置
     *
     * @param id
     * @param orderSetting
     * @return
     */
    @RequestMapping(value = "orderSetting/update/{id}", method = RequestMethod.POST)
    int update(@PathVariable Long id, @RequestBody OrderSetting orderSetting);
}
