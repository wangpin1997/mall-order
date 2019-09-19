package cn.wpin.mall.client.order;

import cn.wpin.mall.common.entity.CommonResult;
import cn.wpin.mall.portal.entity.ConfirmOrderResult;
import cn.wpin.mall.portal.entity.OrderParam;
import cn.wpin.mall.user.entity.Member;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wangpin
 */
@FeignClient("order")
public interface PortalOrderClient {

    /**
     * 根据购物车信息生成确认单信息
     *
     * @param member
     * @return
     */
    @RequestMapping(value = "portal/order/generateConfirmOrder", method = RequestMethod.POST)
    CommonResult<ConfirmOrderResult> generateConfirmOrder(@RequestBody Member member);

    /**
     * 根据购物车信息生成订单
     *
     * @param orderParam
     * @param member
     * @param orderSn
     * @return
     */
    @RequestMapping(value = "portal/order/generateOrder", method = RequestMethod.POST)
    Object generateOrder(@RequestBody OrderParam orderParam,
                         @RequestBody Member member,
                         @RequestParam String orderSn);

    /**
     * 支付成功的回调
     *
     * @param orderId
     * @return
     */
    @RequestMapping(value = "portal/order/paySuccess", method = RequestMethod.POST)
    Object paySuccess(@RequestParam Long orderId);

    /**
     * 自动取消超时订单
     *
     * @return
     */
    @RequestMapping(value = "portal/order/cancelTimeOutOrder", method = RequestMethod.POST)
    Object cancelTimeOutOrder();

    /**
     * 取消单个超时订单
     *
     * @param orderId
     * @return
     */
    @RequestMapping(value = "portal/order/cancelOrder", method = RequestMethod.POST)
    CommonResult cancelOrder(Long orderId);
}
