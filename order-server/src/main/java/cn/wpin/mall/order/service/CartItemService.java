package cn.wpin.mall.order.service;


import cn.wpin.mall.order.entity.CartItem;
import cn.wpin.mall.order.example.CartItemExample;
import cn.wpin.mall.order.mapper.CartItemMapper;
import cn.wpin.mall.sale.entity.CartPromotionItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 购物车管理Service实现类
 *
 * @author wangpin
 */
@Service
public class CartItemService {
    @Autowired
    private CartItemMapper cartItemMapper;

    @Autowired
    private PromotionService promotionService;


    public int add(CartItem cartItem, Long id, String name) {
        int count;
        cartItem.setMemberId(id);
        cartItem.setMemberNickname(name);
        cartItem.setDeleteStatus(0);
        CartItem existCartItem = getCartItem(cartItem);
        if (existCartItem == null) {
            cartItem.setCreateDate(new Date());
            count = cartItemMapper.insert(cartItem);
        } else {
            cartItem.setModifyDate(new Date());
            existCartItem.setQuantity(existCartItem.getQuantity() + cartItem.getQuantity());
            count = cartItemMapper.updateByPrimaryKey(existCartItem);
        }
        return count;
    }

    /**
     * 根据会员id,商品id和规格获取购物车中商品
     */
    private CartItem getCartItem(CartItem cartItem) {
        CartItemExample example = new CartItemExample();
        CartItemExample.Criteria criteria = example.createCriteria().andMemberIdEqualTo(cartItem.getMemberId())
                .andProductIdEqualTo(cartItem.getProductId()).andDeleteStatusEqualTo(0);
        if (!StringUtils.isEmpty(cartItem.getSp1())) {
            criteria.andSp1EqualTo(cartItem.getSp1());
        }
        if (!StringUtils.isEmpty(cartItem.getSp2())) {
            criteria.andSp2EqualTo(cartItem.getSp2());
        }
        if (!StringUtils.isEmpty(cartItem.getSp3())) {
            criteria.andSp3EqualTo(cartItem.getSp3());
        }
        List<CartItem> cartItemList = cartItemMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(cartItemList)) {
            return cartItemList.get(0);
        }
        return null;
    }

    public List<CartItem> list(Long memberId) {
        CartItemExample example = new CartItemExample();
        example.createCriteria().andDeleteStatusEqualTo(0).andMemberIdEqualTo(memberId);
        return cartItemMapper.selectByExample(example);
    }

    public List<CartPromotionItem> listPromotion(Long id) {
        List<CartItem> cartItemList = list(id);
        List<CartPromotionItem> cartPromotionItemList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(cartItemList)) {
            cartPromotionItemList = promotionService.calcCartPromotion(cartItemList);
        }
        return cartPromotionItemList;
    }

    public int updateQuantity(Long id, Long memberId, Integer quantity) {
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(quantity);
        CartItemExample example = new CartItemExample();
        example.createCriteria().andDeleteStatusEqualTo(0)
                .andIdEqualTo(id).andMemberIdEqualTo(memberId);
        return cartItemMapper.updateByExampleSelective(cartItem, example);
    }

    public int delete(Long memberId, List<Long> ids) {
        CartItem record = new CartItem();
        record.setDeleteStatus(1);
        CartItemExample example = new CartItemExample();
        example.createCriteria().andIdIn(ids).andMemberIdEqualTo(memberId);
        return cartItemMapper.updateByExampleSelective(record, example);
    }


    public int updateAttr(CartItem cartItem, Long id, String name) {
        //删除原购物车信息
        CartItem updateCart = new CartItem();
        updateCart.setId(cartItem.getId());
        updateCart.setModifyDate(new Date());
        updateCart.setDeleteStatus(1);
        cartItemMapper.updateByPrimaryKeySelective(updateCart);
        cartItem.setId(null);
        add(cartItem, id, name);
        return 1;
    }

    public int clear(Long memberId) {
        CartItem record = new CartItem();
        record.setDeleteStatus(1);
        CartItemExample example = new CartItemExample();
        example.createCriteria().andMemberIdEqualTo(memberId);
        return cartItemMapper.updateByExampleSelective(record, example);
    }
}
