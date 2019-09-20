package cn.wpin.mall.client.order;

import cn.wpin.mall.common.entity.CommonResult;
import cn.wpin.mall.order.entity.CartItem;
import cn.wpin.mall.portal.entity.CartPromotionItem;
import cn.wpin.mall.user.entity.Member;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wangpin
 */
@FeignClient("order")
public interface CartItemClient {

    /**
     * 添加商品到购物车
     *
     * @param cartItem
     * @param id
     * @param name
     * @return
     */
    @RequestMapping(value = "cart/add", method = RequestMethod.POST)
    CommonResult add(@RequestBody CartItem cartItem, @RequestParam Long id, @RequestParam String name);

    /**
     * 获取某个会员的购物车列表
     *
     * @param currentMember
     * @return
     */
    @RequestMapping(value = "cart/list", method = RequestMethod.GET)
    CommonResult<List<CartItem>> list(@RequestBody Member currentMember);

    /**
     * 修改购物车中某个商品的数量
     *
     * @param id
     * @param quantity
     * @param currentMember
     * @return
     */
    @RequestMapping(value = "cart/update/quantity", method = RequestMethod.GET)
    CommonResult updateQuantity(@RequestParam Long id,
                                @RequestParam Integer quantity,
                                @RequestBody Member currentMember);


    /**
     * 修改购物车中商品的规格
     *
     * @param cartItem
     * @param id
     * @param name
     * @return
     */
    @RequestMapping(value = "cart/update/attr", method = RequestMethod.POST)
    CommonResult updateAttr(@RequestBody CartItem cartItem,
                            @RequestParam Long id,
                            @RequestParam String name);

    /**
     * 删除购物车中的某个商品
     *
     * @param ids
     * @param currentMember
     * @return
     */
    @RequestMapping(value = "cart/delete", method = RequestMethod.POST)
    CommonResult delete(@RequestParam("ids") List<Long> ids,
                        @RequestBody Member currentMember);

    /**
     * 清空购物车
     *
     * @param currentMember
     * @return
     */
    @RequestMapping(value = "cart/clear", method = RequestMethod.POST)
    CommonResult clear(@RequestBody Member currentMember);

    @GetMapping("cart/listPromotion")
    List<CartPromotionItem> listPromotion(@RequestParam Long id);
}
