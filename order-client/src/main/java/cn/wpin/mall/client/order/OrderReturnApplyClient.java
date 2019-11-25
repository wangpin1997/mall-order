package cn.wpin.mall.client.order;

import cn.wpin.mall.common.entity.CommonPage;
import cn.wpin.mall.order.dto.OrderReturnApplyResult;
import cn.wpin.mall.order.dto.ReturnApplyQueryParam;
import cn.wpin.mall.order.dto.UpdateStatusParam;
import cn.wpin.mall.order.entity.OrderReturnApply;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wangpin
 */
@FeignClient("order")
public interface OrderReturnApplyClient {

    /**
     * 分页查询退货申请
     *
     * @param queryParam
     * @param pageSize
     * @param pageNum
     * @return
     */
    @RequestMapping(value = "returnApply/list", method = RequestMethod.POST)
    CommonPage<OrderReturnApply> list(@RequestBody ReturnApplyQueryParam queryParam,
                                      @RequestParam(value = "pageSize") Integer pageSize,
                                      @RequestParam(value = "pageNum") Integer pageNum);

    /**
     * 批量删除申请
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "returnApply/delete", method = RequestMethod.POST)
    int delete(@RequestParam("ids") List<Long> ids);

    /**
     * 获取退货申请详情
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "returnApply/{id}", method = RequestMethod.GET)
    OrderReturnApplyResult getItem(@PathVariable("id") Long id);

    /**
     * 修改申请状态
     *
     * @param id
     * @param statusParam
     * @return
     */
    @RequestMapping(value = "returnApply/update/status/{id}", method = RequestMethod.POST)
    int updateStatus(@PathVariable("id") Long id, @RequestBody UpdateStatusParam statusParam);
}
