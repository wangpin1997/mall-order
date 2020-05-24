package cn.wpin.mall.client.order;

import cn.wpin.mall.common.entity.CommonResult;
import cn.wpin.mall.order.dto.OrderReturnApplyParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author wangpin
 */
@FeignClient("order")
public interface PortalOrderReturnApplyClient {

    /**
     * 申请退货
     */
    @RequestMapping(value = "returnApply/create", method = RequestMethod.POST)
    CommonResult create(@RequestBody OrderReturnApplyParam returnApply);
}
