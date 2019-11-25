package cn.wpin.mall.order.controller;

import cn.wpin.mall.common.entity.CommonPage;
import cn.wpin.mall.order.dto.OrderReturnApplyResult;
import cn.wpin.mall.order.dto.ReturnApplyQueryParam;
import cn.wpin.mall.order.dto.UpdateStatusParam;
import cn.wpin.mall.order.entity.OrderReturnApply;
import cn.wpin.mall.order.service.OrderReturnApplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单退货申请管理
 *
 * @author wangpin
 */
@RestController
@Api(tags = "订单退货申请管理")
@RequestMapping("/returnApply")
public class OrderReturnApplyController {

    @Autowired
    private OrderReturnApplyService returnApplyService;

    @ApiOperation("分页查询退货申请")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public CommonPage<OrderReturnApply> list(@RequestBody ReturnApplyQueryParam queryParam,
                                             @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                             @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<OrderReturnApply> returnApplyList = returnApplyService.list(queryParam, pageSize, pageNum);
        return CommonPage.restPage(returnApplyList);
    }

    @ApiOperation("批量删除申请")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public int delete(@RequestParam("ids") List<Long> ids) {
        return returnApplyService.delete(ids);
    }

    @ApiOperation("获取退货申请详情")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public OrderReturnApplyResult getItem(@PathVariable("id") Long id) {
        return returnApplyService.getItem(id);
    }

    @ApiOperation("修改申请状态")
    @RequestMapping(value = "/update/status/{id}", method = RequestMethod.POST)
    public int updateStatus(@PathVariable("id") Long id, @RequestBody UpdateStatusParam statusParam) {
        return returnApplyService.updateStatus(id, statusParam);
    }

}
