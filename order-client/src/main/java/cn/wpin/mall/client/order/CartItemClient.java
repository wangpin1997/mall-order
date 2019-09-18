package cn.wpin.mall.client.order;

import cn.wpin.mall.common.entity.CommonResult;
import cn.wpin.mall.order.entity.CartItem;
import cn.wpin.mall.user.entity.Member;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author wangpin
 */
@FeignClient("order")
public interface CartItemClient {

    @ApiOperation("添加商品到购物车")
    @RequestMapping(value = "cart/add", method = RequestMethod.POST)
    CommonResult add(@RequestBody CartItem cartItem, @RequestBody Member currentMember);

    @ApiOperation("获取某个会员的购物车列表")
    @RequestMapping(value = "cart/list", method = RequestMethod.GET)
    CommonResult<List<CartItem>> list(@RequestBody Member currentMember);

    @ApiOperation("修改购物车中某个商品的数量")
    @RequestMapping(value = "cart/update/quantity", method = RequestMethod.GET)
    CommonResult updateQuantity(@RequestParam Long id,
                                @RequestParam Integer quantity,
                                @RequestBody Member currentMember);


    @ApiOperation("修改购物车中商品的规格")
    @RequestMapping(value = "cart/update/attr", method = RequestMethod.POST)
    CommonResult updateAttr(@RequestBody CartItem cartItem,
                            @RequestBody Member currentMember);

    @ApiOperation("删除购物车中的某个商品")
    @RequestMapping(value = "cart/delete", method = RequestMethod.POST)
    CommonResult delete(@RequestParam("ids") List<Long> ids,
                        @RequestBody Member currentMember);

    @ApiOperation("清空购物车")
    @RequestMapping(value = "cart/clear", method = RequestMethod.POST)
    CommonResult clear(@RequestBody Member currentMember);
}
