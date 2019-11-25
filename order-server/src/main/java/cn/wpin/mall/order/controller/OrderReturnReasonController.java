package cn.wpin.mall.order.controller;

import cn.wpin.mall.common.entity.CommonPage;
import cn.wpin.mall.order.entity.OrderReturnReason;
import cn.wpin.mall.order.service.OrderReturnReasonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 退货原因管理Controller
 *
 * @author wangpin
 */
@RestController
@Api(tags = "退货原因管理")
@RequestMapping("/returnReason")
public class OrderReturnReasonController {

    @Autowired
    private OrderReturnReasonService orderReturnReasonService;

    @ApiOperation("添加退货原因")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public int create(@RequestBody OrderReturnReason returnReason) {
        return orderReturnReasonService.create(returnReason);
    }

    @ApiOperation("修改退货原因")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public int update(@PathVariable("id") Long id, @RequestBody OrderReturnReason returnReason) {
        return orderReturnReasonService.update(id, returnReason);
    }

    @ApiOperation("批量删除退货原因")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public int delete(@RequestParam("ids") List<Long> ids) {
        return orderReturnReasonService.delete(ids);
    }

    @ApiOperation("分页查询全部退货原因")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonPage<OrderReturnReason> list(@RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                              @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<OrderReturnReason> reasonList = orderReturnReasonService.list(pageSize, pageNum);
        return CommonPage.restPage(reasonList);
    }

    @ApiOperation("获取单个退货原因详情信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public OrderReturnReason getItem(@PathVariable("id") Long id) {
        return orderReturnReasonService.getItem(id);
    }

    @ApiOperation("修改退货原因启用状态")
    @RequestMapping(value = "/update/status", method = RequestMethod.POST)
    public int updateStatus(@RequestParam(value = "status") Integer status,
                            @RequestParam("ids") List<Long> ids) {
        return orderReturnReasonService.updateStatus(ids, status);
    }
}
