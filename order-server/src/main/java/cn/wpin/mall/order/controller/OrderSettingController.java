package cn.wpin.mall.order.controller;

import cn.wpin.mall.common.entity.CommonResult;
import cn.wpin.mall.order.entity.OrderSetting;
import cn.wpin.mall.order.service.OrderSettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 订单设置Controller
 * @author wangpin
 */
@RestController
@Api(tags = "订单设置管理")
@RequestMapping("/orderSetting")
public class OrderSettingController {
    @Autowired
    private OrderSettingService orderSettingService;

    @ApiOperation("获取指定订单设置")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public OrderSetting getItem(@PathVariable Long id) {
        return orderSettingService.getItem(id);
    }

    @ApiOperation("修改指定订单设置")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public int update(@PathVariable Long id, @RequestBody OrderSetting orderSetting) {
        return orderSettingService.update(id,orderSetting);
    }
}
