package cn.wpin.mall.order.controller;

import cn.wpin.mall.common.entity.CommonPage;
import cn.wpin.mall.order.dto.*;
import cn.wpin.mall.order.entity.Order;
import cn.wpin.mall.order.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单管理Controller
 *
 * @author wangpin
 */
@RestController
@Api(tags = "订单管理")
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation("查询订单")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public CommonPage<Order> list(@RequestBody OrderQueryParam queryParam,
                                  @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                  @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<Order> orderList = orderService.list(queryParam, pageSize, pageNum);
        return CommonPage.restPage(orderList);
    }

    @ApiOperation("批量发货")
    @RequestMapping(value = "/update/delivery", method = RequestMethod.POST)
    public int delivery(@RequestBody List<OrderDeliveryParam> deliveryParamList) {
        return orderService.delivery(deliveryParamList);
    }

    @ApiOperation("批量关闭订单")
    @RequestMapping(value = "/update/close", method = RequestMethod.POST)
    public int close(@RequestParam("ids") List<Long> ids, @RequestParam("note") String note) {
        return orderService.close(ids, note);
    }

    @ApiOperation("批量删除订单")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public int delete(@RequestParam("ids") List<Long> ids) {
        return orderService.delete(ids);
    }

    @ApiOperation("获取订单详情:订单信息、商品信息、操作记录")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public OrderDetail detail(@PathVariable Long id) {
        return orderService.detail(id);
    }

    @ApiOperation("修改收货人信息")
    @RequestMapping(value = "/update/receiverInfo", method = RequestMethod.POST)
    public int updateReceiverInfo(@RequestBody ReceiverInfoParam receiverInfoParam) {
        return orderService.updateReceiverInfo(receiverInfoParam);
    }

    @ApiOperation("修改订单费用信息")
    @RequestMapping(value = "/update/moneyInfo", method = RequestMethod.POST)
    public int updateReceiverInfo(@RequestBody MoneyInfoParam moneyInfoParam) {
        return orderService.updateMoneyInfo(moneyInfoParam);
    }

    @ApiOperation("备注订单")
    @RequestMapping(value = "/update/note", method = RequestMethod.POST)
    public int updateNote(@RequestParam("id") Long id,
                          @RequestParam("note") String note,
                          @RequestParam("status") Integer status) {
        return orderService.updateNote(id, note, status);
    }
}
