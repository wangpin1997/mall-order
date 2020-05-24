package cn.wpin.mall.order.controller;


import cn.wpin.mall.common.entity.CommonResult;
import cn.wpin.mall.order.entity.CartItem;
import cn.wpin.mall.order.service.CartItemService;
import cn.wpin.mall.sale.entity.CartPromotionItem;
import cn.wpin.mall.user.entity.Member;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 购物车管理Controller
 * @author wangpin 
 */
@RestController
@Api(tags = "购物车管理")
@RequestMapping("/cart")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @ApiOperation("添加商品到购物车")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonResult add(@RequestBody CartItem cartItem,@RequestParam("id") Long id,@RequestParam("name") String name) {
        int count = cartItemService.add(cartItem,id,name);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("获取某个会员的购物车列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public CommonResult<List<CartItem>> list(@RequestBody Member currentMember) {
        List<CartItem> cartItemList = cartItemService.list(currentMember.getId());
        return CommonResult.success(cartItemList);
    }

    @GetMapping("listPromotion")
    public List<CartPromotionItem> listPromotion(@RequestParam("id") Long id) {
        return cartItemService.listPromotion(id);
    }


    @ApiOperation("修改购物车中某个商品的数量")
    @RequestMapping(value = "/update/quantity", method = RequestMethod.POST)
    public CommonResult updateQuantity(@RequestParam("id") Long id,
                                       @RequestParam("quantity") Integer quantity,
                                       @RequestBody Member currentMember) {
        int count = cartItemService.updateQuantity(id, currentMember.getId(), quantity);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }


    @ApiOperation("修改购物车中商品的规格")
    @RequestMapping(value = "/update/attr", method = RequestMethod.POST)
    public CommonResult updateAttr(@RequestBody CartItem cartItem,
                                   @RequestParam("id") Long id,
                                   @RequestParam("name") String name) {
        int count = cartItemService.updateAttr(cartItem,id,name);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("删除购物车中的某个商品")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public CommonResult delete(@RequestParam("ids") List<Long> ids,
                               @RequestBody Member currentMember) {
        int count = cartItemService.delete(currentMember.getId(), ids);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("清空购物车")
    @RequestMapping(value = "/clear", method = RequestMethod.POST)
    public CommonResult clear(@RequestBody Member currentMember) {
        int count = cartItemService.clear(currentMember.getId());
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }
}
