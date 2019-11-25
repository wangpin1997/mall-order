package cn.wpin.mall.client.order;

import cn.wpin.mall.common.entity.CommonPage;
import cn.wpin.mall.order.entity.OrderReturnReason;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wangpin
 */
@FeignClient("order")
public interface OrderReturnReasonClient {

     /**
     * 添加退货原因
     *
     * @param returnReason
     * @return
     */
    @RequestMapping(value = "returnReason/create", method = RequestMethod.POST)
    int create(@RequestBody OrderReturnReason returnReason);


    /**
     * 修改退货原因
     *
     * @param id
     * @param returnReason
     * @return
     */
    @RequestMapping(value = "returnReason/update/{id}", method = RequestMethod.POST)
    int update(@PathVariable("id") Long id, @RequestBody OrderReturnReason returnReason);

    /**
     * 批量删除退货原因
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "returnReason/delete", method = RequestMethod.POST)
    int delete(@RequestParam("ids") List<Long> ids);

    /**
     * 分页查询全部退货原因
     *
     * @param pageSize
     * @param pageNum
     * @return
     */
    @RequestMapping(value = "returnReason/list", method = RequestMethod.GET)
    CommonPage<OrderReturnReason> list(@RequestParam(value = "pageSize") Integer pageSize,
                                       @RequestParam(value = "pageNum") Integer pageNum);

    /**
     * 获取单个退货原因详情信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "returnReason/{id}", method = RequestMethod.GET)
    OrderReturnReason getItem(@PathVariable("id") Long id);

    /**
     * 修改退货原因启用状态
     *
     * @param status
     * @param ids
     * @return
     */
    @RequestMapping(value = "returnReason/update/status", method = RequestMethod.POST)
    int updateStatus(@RequestParam(value = "status") Integer status,
                     @RequestParam("ids") List<Long> ids);
}
