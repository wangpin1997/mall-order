package cn.wpin.mall.client.order;

import cn.wpin.mall.common.entity.CommonPage;
import cn.wpin.mall.order.dto.*;
import cn.wpin.mall.order.entity.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wangpin
 */
@FeignClient("order")
public interface OrderClient {

    /**
     * 查询订单
     *
     * @param queryParam
     * @param pageSize
     * @param pageNum
     * @return
     */
    @RequestMapping(value = "order/list", method = RequestMethod.GET)
    CommonPage<Order> list(OrderQueryParam queryParam,
                           @RequestParam(value = "pageSize") Integer pageSize,
                           @RequestParam(value = "pageNum") Integer pageNum);

    /**
     * 批量发货
     *
     * @param deliveryParamList
     * @return
     */
    @RequestMapping(value = "order/update/delivery", method = RequestMethod.POST)
    int delivery(@RequestBody List<OrderDeliveryParam> deliveryParamList);

    /**
     * 批量关闭订单
     *
     * @param ids
     * @param note
     * @return
     */
    @RequestMapping(value = "order/update/close", method = RequestMethod.POST)
    int close(@RequestParam("ids") List<Long> ids, @RequestParam String note);

    /**
     * 批量删除订单
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "order/delete", method = RequestMethod.POST)
    int delete(@RequestParam("ids") List<Long> ids);

    /**
     * 获取订单详情:订单信息、商品信息、操作记录
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "order/{id}", method = RequestMethod.GET)
    OrderDetail detail(@PathVariable Long id);

    /**
     * 修改收货人信息
     *
     * @param receiverInfoParam
     * @return
     */
    @RequestMapping(value = "order/update/receiverInfo", method = RequestMethod.POST)
    int updateReceiverInfo(@RequestBody ReceiverInfoParam receiverInfoParam);

    /**
     * 修改订单费用信息
     *
     * @param moneyInfoParam
     * @return
     */
    @RequestMapping(value = "order/update/moneyInfo", method = RequestMethod.POST)
    int updateReceiverInfo(@RequestBody MoneyInfoParam moneyInfoParam);

    /**
     * 备注订单
     *
     * @param id
     * @param note
     * @param status
     * @return
     */
    @RequestMapping(value = "order/update/note", method = RequestMethod.POST)
    int updateNote(@RequestParam("id") Long id,
                   @RequestParam("note") String note,
                   @RequestParam("status") Integer status);
}
