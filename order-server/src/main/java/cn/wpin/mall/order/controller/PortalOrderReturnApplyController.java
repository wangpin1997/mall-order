package cn.wpin.mall.order.controller;


import cn.wpin.mall.common.entity.CommonResult;
import cn.wpin.mall.order.dto.OrderReturnApplyParam;
import cn.wpin.mall.order.service.PortalOrderReturnApplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 申请退货管理Controller
 *
 * @author wangpin
 */
@RestController
@Api(tags = "申请退货管理")
@RequestMapping("/returnApply")
public class PortalOrderReturnApplyController {


    @Autowired
    private PortalOrderReturnApplyService returnApplyService;

    @ApiOperation("申请退货")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public CommonResult create(@RequestBody OrderReturnApplyParam returnApply) {
        int count = returnApplyService.create(returnApply);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }
}
