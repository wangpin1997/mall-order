package cn.wpin.mall.order.controller;


import cn.wpin.mall.common.entity.CommonResult;
import cn.wpin.mall.order.entity.ConfirmOrderResult;
import cn.wpin.mall.order.entity.OrderParam;
import cn.wpin.mall.order.service.PortalOrderService;
import cn.wpin.mall.user.entity.Member;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 订单管理Controller
 *
 * @author wangpin
 */
@RestController
@Api(tags = "订单管理")
@RequestMapping("portal/order")
public class PortalOrderController {


    @Autowired
    private PortalOrderService portalOrderService;

    @ApiOperation("根据购物车信息生成确认单信息")
    @RequestMapping(value = "/generateConfirmOrder", method = RequestMethod.POST)
    public CommonResult<ConfirmOrderResult> generateConfirmOrder(@RequestBody Member member) {
        ConfirmOrderResult confirmOrderResult = portalOrderService.generateConfirmOrder(member);
        return CommonResult.success(confirmOrderResult);
    }

    @ApiOperation("根据购物车信息生成订单")
    @RequestMapping(value = "/generateOrder", method = RequestMethod.POST)
    public Object generateOrder(@RequestBody OrderParam orderParam,
                                @RequestParam("orderSn") String orderSn) {
        return portalOrderService.generateOrder(orderParam, orderParam.getMember(), orderSn);
    }

    @ApiOperation("支付成功的回调")
    @RequestMapping(value = "/paySuccess", method = RequestMethod.POST)
    public Object paySuccess(@RequestParam("orderId") Long orderId) {
        return portalOrderService.paySuccess(orderId);
    }

    @ApiOperation("自动取消超时订单")
    @RequestMapping(value = "/cancelTimeOutOrder", method = RequestMethod.POST)
    public Object cancelTimeOutOrder() {
        return portalOrderService.cancelTimeOutOrder();
    }

    @ApiOperation("取消单个超时订单")
    @RequestMapping(value = "/sendDelayMessageCancelOrder", method = RequestMethod.POST)
    public long sendDelayMessageCancelOrder(@RequestParam("orderId") Long orderId) {
        return portalOrderService.sendDelayMessageCancelOrder(orderId);
    }

    @PostMapping("/cancelOrder")
    public void cancelOrder(@RequestParam("orderId") Long orderId) {
        portalOrderService.cancelOrder(orderId);
    }
}
