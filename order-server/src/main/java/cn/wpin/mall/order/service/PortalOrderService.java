package cn.wpin.mall.order.service;

import cn.wpin.mall.client.product.SkuStockClient;
import cn.wpin.mall.client.sale.CouponHistoryClient;
import cn.wpin.mall.client.sale.MemberCouponClient;
import cn.wpin.mall.client.user.IntegrationConsumeSettingClient;
import cn.wpin.mall.client.user.MemberClient;
import cn.wpin.mall.client.user.MemberReceiveAddressClient;
import cn.wpin.mall.common.entity.CommonResult;
import cn.wpin.mall.order.dao.PortalOrderDao;
import cn.wpin.mall.order.dao.PortalOrderItemDao;
import cn.wpin.mall.order.dto.OrderDetail;
import cn.wpin.mall.order.entity.Order;
import cn.wpin.mall.order.entity.OrderItem;
import cn.wpin.mall.order.entity.OrderSetting;
import cn.wpin.mall.order.example.OrderExample;
import cn.wpin.mall.order.example.OrderItemExample;
import cn.wpin.mall.order.mapper.OrderItemMapper;
import cn.wpin.mall.order.mapper.OrderMapper;
import cn.wpin.mall.order.mapper.OrderSettingMapper;
import cn.wpin.mall.portal.entity.CartPromotionItem;
import cn.wpin.mall.portal.entity.ConfirmOrderResult;
import cn.wpin.mall.portal.entity.CouponHistoryDetail;
import cn.wpin.mall.portal.entity.OrderParam;
import cn.wpin.mall.product.entity.SkuStock;
import cn.wpin.mall.sale.entity.Coupon;
import cn.wpin.mall.sale.entity.CouponProductCategoryRelation;
import cn.wpin.mall.sale.entity.CouponProductRelation;
import cn.wpin.mall.user.entity.IntegrationConsumeSetting;
import cn.wpin.mall.user.entity.Member;
import cn.wpin.mall.user.entity.MemberReceiveAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * 前台订单管理Service
 *
 * @author wangpin
 */
@Service
public class PortalOrderService {


    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private PortalOrderItemDao orderItemDao;

    @Autowired
    private PortalOrderDao portalOrderDao;
    @Autowired
    private OrderSettingMapper orderSettingMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private MemberClient memberClient;

    @Autowired
    private IntegrationConsumeSettingClient integrationConsumeSettingClient;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private MemberReceiveAddressClient memberReceiveAddressClient;

    @Autowired
    private MemberCouponClient memberCouponClient;

    @Autowired
    private CouponHistoryClient couponHistoryClient;

    @Autowired
    private SkuStockClient skuStockClient;


    public ConfirmOrderResult generateConfirmOrder(Member currentMember) {
        ConfirmOrderResult result = new ConfirmOrderResult();
        //获取购物车信息
        List<CartPromotionItem> cartPromotionItemList = cartItemService.listPromotion(currentMember.getId());
        result.setCartPromotionItemList(cartPromotionItemList);
        //获取用户收货地址列表
        List<MemberReceiveAddress> memberReceiveAddressList = memberReceiveAddressClient.list(currentMember);
        result.setMemberReceiveAddressList(memberReceiveAddressList);
        //获取用户可用优惠券列表
        List<CouponHistoryDetail> couponHistoryDetailList = memberCouponClient.listCart(cartPromotionItemList, 1,currentMember).getData();
        result.setCouponHistoryDetailList(couponHistoryDetailList);
        //获取用户积分
        result.setMemberIntegration(currentMember.getIntegration());
        //获取积分使用规则
        IntegrationConsumeSetting integrationConsumeSetting = integrationConsumeSettingClient.selectByKey(1L);
        result.setIntegrationConsumeSetting(integrationConsumeSetting);
        //计算总金额、活动优惠、应付金额
        ConfirmOrderResult.CalcAmount calcAmount = calcCartAmount(cartPromotionItemList);
        result.setCalcAmount(calcAmount);
        return result;
    }

    public CommonResult generateOrder(OrderParam orderParam, Member currentMember, String orderSn) {
        List<OrderItem> orderItemList = new ArrayList<>();
        //获取购物车及优惠信息
        List<CartPromotionItem> cartPromotionItemList = cartItemService.listPromotion(currentMember.getId());
        for (CartPromotionItem cartPromotionItem : cartPromotionItemList) {
            //生成下单商品信息
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(cartPromotionItem.getProductId());
            orderItem.setProductName(cartPromotionItem.getProductName());
            orderItem.setProductPic(cartPromotionItem.getProductPic());
            orderItem.setProductAttr(cartPromotionItem.getProductAttr());
            orderItem.setProductBrand(cartPromotionItem.getProductBrand());
            orderItem.setProductSn(cartPromotionItem.getProductSn());
            orderItem.setProductPrice(cartPromotionItem.getPrice());
            orderItem.setProductQuantity(cartPromotionItem.getQuantity());
            orderItem.setProductSkuId(cartPromotionItem.getProductSkuId());
            orderItem.setProductSkuCode(cartPromotionItem.getProductSkuCode());
            orderItem.setProductCategoryId(cartPromotionItem.getProductCategoryId());
            orderItem.setPromotionAmount(cartPromotionItem.getReduceAmount());
            orderItem.setPromotionName(cartPromotionItem.getPromotionMessage());
            orderItem.setGiftIntegration(cartPromotionItem.getIntegration());
            orderItem.setGiftGrowth(cartPromotionItem.getGrowth());
            orderItemList.add(orderItem);
        }
        //判断购物车中商品是否都有库存
        if (!hasStock(cartPromotionItemList)) {
            return CommonResult.failed("库存不足，无法下单");
        }
        //判断使用使用了优惠券
        if (orderParam.getCouponId() == null) {
            //不用优惠券
            for (OrderItem orderItem : orderItemList) {
                orderItem.setCouponAmount(new BigDecimal(0));
            }
        } else {
            //使用优惠券
            CouponHistoryDetail couponHistoryDetail = getUseCoupon(cartPromotionItemList, orderParam.getCouponId(), currentMember);
            if (couponHistoryDetail == null) {
                return CommonResult.failed("该优惠券不可用");
            }
            //对下单商品的优惠券进行处理
            handleCouponAmount(orderItemList, couponHistoryDetail);
        }
        //判断是否使用积分
        if (orderParam.getUseIntegration() == null) {
            //不使用积分
            for (OrderItem orderItem : orderItemList) {
                orderItem.setIntegrationAmount(new BigDecimal(0));
            }
        } else {
            //使用积分
            BigDecimal totalAmount = calcTotalAmount(orderItemList);
            BigDecimal integrationAmount = getUseIntegrationAmount(orderParam.getUseIntegration(), totalAmount, currentMember, orderParam.getCouponId() != null);
            if (integrationAmount.compareTo(new BigDecimal(0)) == 0) {
                return CommonResult.failed("积分不可用");
            } else {
                //可用情况下分摊到可用商品中
                for (OrderItem orderItem : orderItemList) {
                    BigDecimal perAmount = orderItem.getProductPrice().divide(totalAmount, 3, RoundingMode.HALF_EVEN).multiply(integrationAmount);
                    orderItem.setIntegrationAmount(perAmount);
                }
            }
        }
        //计算order_item的实付金额
        handleRealAmount(orderItemList);
        //进行库存锁定
        lockStock(cartPromotionItemList);
        //根据商品合计、运费、活动优惠、优惠券、积分计算应付金额
        Order order = new Order();
        order.setDiscountAmount(new BigDecimal(0));
        order.setTotalAmount(calcTotalAmount(orderItemList));
        order.setFreightAmount(new BigDecimal(0));
        order.setPromotionAmount(calcPromotionAmount(orderItemList));
        order.setPromotionInfo(getOrderPromotionInfo(orderItemList));
        if (orderParam.getCouponId() == null) {
            order.setCouponAmount(new BigDecimal(0));
        } else {
            order.setCouponId(orderParam.getCouponId());
            order.setCouponAmount(calcCouponAmount(orderItemList));
        }
        if (orderParam.getUseIntegration() == null) {
            order.setIntegration(0);
            order.setIntegrationAmount(new BigDecimal(0));
        } else {
            order.setIntegration(orderParam.getUseIntegration());
            order.setIntegrationAmount(calcIntegrationAmount(orderItemList));
        }
        order.setPayAmount(calcPayAmount(order));
        //转化为订单信息并插入数据库
        order.setMemberId(currentMember.getId());
        order.setCreateTime(new Date());
        order.setMemberUsername(currentMember.getUsername());
        //支付方式：0->未支付；1->支付宝；2->微信
        order.setPayType(orderParam.getPayType());
        //订单来源：0->PC订单；1->app订单
        order.setSourceType(1);
        //订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单
        order.setStatus(0);
        //订单类型：0->正常订单；1->秒杀订单
        order.setOrderType(0);
        //收货人信息：姓名、电话、邮编、地址
        MemberReceiveAddress address = memberReceiveAddressClient.getItem(orderParam.getMemberReceiveAddressId(), currentMember);
        order.setReceiverName(address.getName());
        order.setReceiverPhone(address.getPhoneNumber());
        order.setReceiverPostCode(address.getPostCode());
        order.setReceiverProvince(address.getProvince());
        order.setReceiverCity(address.getCity());
        order.setReceiverRegion(address.getRegion());
        order.setReceiverDetailAddress(address.getDetailAddress());
        //0->未确认；1->已确认
        order.setConfirmStatus(0);
        order.setDeleteStatus(0);
        //计算赠送积分
        order.setIntegration(calcGifIntegration(orderItemList));
        //计算赠送成长值
        order.setGrowth(calcGiftGrowth(orderItemList));
        //生成订单号
        order.setOrderSn(orderSn);
        // TODO: 2018/9/3 bill_*,delivery_*
        //插入order表和order_item表
        orderMapper.insert(order);
        for (OrderItem orderItem : orderItemList) {
            orderItem.setOrderId(order.getId());
            orderItem.setOrderSn(order.getOrderSn());
        }
        orderItemDao.insertList(orderItemList);
        //如使用优惠券更新优惠券使用状态
        if (orderParam.getCouponId() != null) {
            updateCouponStatus(orderParam.getCouponId(), currentMember.getId(), 1);
        }
        //如使用积分需要扣除积分
        if (orderParam.getUseIntegration() != null) {
            order.setUseIntegration(orderParam.getUseIntegration());
            memberClient.updateIntegration(currentMember.getId(), currentMember.getIntegration() - orderParam.getUseIntegration());
        }
        //删除购物车中的下单商品
        deleteCartItemList(cartPromotionItemList, currentMember);
        Map<String, Object> result = new HashMap<>();
        result.put("order", order);
        result.put("orderItemList", orderItemList);
        return CommonResult.success(result, "下单成功");
    }

    public CommonResult paySuccess(Long orderId) {
        //修改订单支付状态
        Order order = new Order();
        order.setId(orderId);
        order.setStatus(1);
        order.setPaymentTime(new Date());
        orderMapper.updateByPrimaryKeySelective(order);
        //恢复所有下单商品的锁定库存，扣减真实库存
        OrderDetail orderDetail = portalOrderDao.getDetail(orderId);
        int count = portalOrderDao.updateSkuStock(orderDetail.getOrderItemList());
        return CommonResult.success(count, "支付成功");
    }

    public CommonResult cancelTimeOutOrder() {
        OrderSetting orderSetting = orderSettingMapper.selectByPrimaryKey(1L);
        //查询超时、未支付的订单及订单详情
        List<OrderDetail> timeOutOrders = portalOrderDao.getTimeOutOrders(orderSetting.getNormalOrderOvertime());
        if (CollectionUtils.isEmpty(timeOutOrders)) {
            return CommonResult.failed("暂无超时订单");
        }
        //修改订单状态为交易取消
        List<Long> ids = new ArrayList<>();
        for (OrderDetail timeOutOrder : timeOutOrders) {
            ids.add(timeOutOrder.getId());
        }
        portalOrderDao.updateOrderStatus(ids, 4);
        for (OrderDetail timeOutOrder : timeOutOrders) {
            //解除订单商品库存锁定
            portalOrderDao.releaseSkuStockLock(timeOutOrder.getOrderItemList());
            //修改优惠券使用状态
            updateCouponStatus(timeOutOrder.getCouponId(), timeOutOrder.getMemberId(), 0);
            //返还使用积分
            if (timeOutOrder.getUseIntegration() != null) {
                Member member = memberClient.getById(timeOutOrder.getMemberId());
                memberClient.updateIntegration(timeOutOrder.getMemberId(), member.getIntegration() + timeOutOrder.getUseIntegration());
            }
        }
        return CommonResult.success(null);
    }

    public void cancelOrder(Long orderId) {
        //查询为付款的取消订单
        OrderExample example = new OrderExample();
        example.createCriteria().andIdEqualTo(orderId).andStatusEqualTo(0).andDeleteStatusEqualTo(0);
        List<Order> cancelOrderList = orderMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(cancelOrderList)) {
            return;
        }
        Order cancelOrder = cancelOrderList.get(0);
        if (cancelOrder != null) {
            //修改订单状态为取消
            cancelOrder.setStatus(4);
            orderMapper.updateByPrimaryKeySelective(cancelOrder);
            OrderItemExample orderItemExample = new OrderItemExample();
            orderItemExample.createCriteria().andOrderIdEqualTo(orderId);
            List<OrderItem> orderItemList = orderItemMapper.selectByExample(orderItemExample);
            //解除订单商品库存锁定
            if (!CollectionUtils.isEmpty(orderItemList)) {
                portalOrderDao.releaseSkuStockLock(orderItemList);
            }
            //修改优惠券使用状态
            updateCouponStatus(cancelOrder.getCouponId(), cancelOrder.getMemberId(), 0);
            //返还使用积分
            if (cancelOrder.getUseIntegration() != null) {
                Member member = memberClient.getById(cancelOrder.getMemberId());
                memberClient.updateIntegration(cancelOrder.getMemberId(), member.getIntegration() + cancelOrder.getUseIntegration());
            }
        }
    }

    public long sendDelayMessageCancelOrder(Long orderId) {
        //获取订单超时时间
        OrderSetting orderSetting = orderSettingMapper.selectByPrimaryKey(1L);
        long delayTimes = orderSetting.getNormalOrderOvertime() * 60 * 1000;
        return delayTimes;
    }


    /**
     * 删除下单商品的购物车信息
     */
    private void deleteCartItemList(List<CartPromotionItem> cartPromotionItemList, Member currentMember) {
        List<Long> ids = new ArrayList<>();
        for (CartPromotionItem cartPromotionItem : cartPromotionItemList) {
            ids.add(cartPromotionItem.getId());
        }
        cartItemService.delete(currentMember.getId(), ids);
    }

    /**
     * 计算该订单赠送的成长值
     */
    private Integer calcGiftGrowth(List<OrderItem> orderItemList) {
        Integer sum = 0;
        for (OrderItem orderItem : orderItemList) {
            sum = sum + orderItem.getGiftGrowth() * orderItem.getProductQuantity();
        }
        return sum;
    }

    /**
     * 计算该订单赠送的积分
     */
    private Integer calcGifIntegration(List<OrderItem> orderItemList) {
        int sum = 0;
        for (OrderItem orderItem : orderItemList) {
            sum += orderItem.getGiftIntegration() * orderItem.getProductQuantity();
        }
        return sum;
    }

    /**
     * 将优惠券信息更改为指定状态
     *
     * @param couponId  优惠券id
     * @param memberId  会员id
     * @param useStatus 0->未使用；1->已使用
     */
    private void updateCouponStatus(Long couponId, Long memberId, Integer useStatus) {
        couponHistoryClient.updateCouponStatus(couponId, memberId, useStatus);
    }

    private void handleRealAmount(List<OrderItem> orderItemList) {
        for (OrderItem orderItem : orderItemList) {
            //原价-促销优惠-优惠券抵扣-积分抵扣
            BigDecimal realAmount = orderItem.getProductPrice()
                    .subtract(orderItem.getPromotionAmount())
                    .subtract(orderItem.getCouponAmount())
                    .subtract(orderItem.getIntegrationAmount());
            orderItem.setRealAmount(realAmount);
        }
    }

    /**
     * 获取订单促销信息
     */
    private String getOrderPromotionInfo(List<OrderItem> orderItemList) {
        StringBuilder sb = new StringBuilder();
        for (OrderItem orderItem : orderItemList) {
            sb.append(orderItem.getPromotionName());
            sb.append(",");
        }
        String result = sb.toString();
        if (result.endsWith(",")) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }

    /**
     * 计算订单应付金额
     */
    private BigDecimal calcPayAmount(Order order) {
        //总金额+运费-促销优惠-优惠券优惠-积分抵扣
        BigDecimal payAmount = order.getTotalAmount()
                .add(order.getFreightAmount())
                .subtract(order.getPromotionAmount())
                .subtract(order.getCouponAmount())
                .subtract(order.getIntegrationAmount());
        return payAmount;
    }

    /**
     * 计算订单优惠券金额
     */
    private BigDecimal calcIntegrationAmount(List<OrderItem> orderItemList) {
        BigDecimal integrationAmount = new BigDecimal(0);
        for (OrderItem orderItem : orderItemList) {
            if (orderItem.getIntegrationAmount() != null) {
                integrationAmount = integrationAmount.add(orderItem.getIntegrationAmount().multiply(new BigDecimal(orderItem.getProductQuantity())));
            }
        }
        return integrationAmount;
    }

    /**
     * 计算订单优惠券金额
     */
    private BigDecimal calcCouponAmount(List<OrderItem> orderItemList) {
        BigDecimal couponAmount = new BigDecimal(0);
        for (OrderItem orderItem : orderItemList) {
            if (orderItem.getCouponAmount() != null) {
                couponAmount = couponAmount.add(orderItem.getCouponAmount().multiply(new BigDecimal(orderItem.getProductQuantity())));
            }
        }
        return couponAmount;
    }

    /**
     * 计算订单活动优惠
     */
    private BigDecimal calcPromotionAmount(List<OrderItem> orderItemList) {
        BigDecimal promotionAmount = new BigDecimal(0);
        for (OrderItem orderItem : orderItemList) {
            if (orderItem.getPromotionAmount() != null) {
                promotionAmount = promotionAmount.add(orderItem.getPromotionAmount().multiply(new BigDecimal(orderItem.getProductQuantity())));
            }
        }
        return promotionAmount;
    }

    /**
     * 获取可用积分抵扣金额
     *
     * @param useIntegration 使用的积分数量
     * @param totalAmount    订单总金额
     * @param currentMember  使用的用户
     * @param hasCoupon      是否已经使用优惠券
     */
    private BigDecimal getUseIntegrationAmount(Integer useIntegration, BigDecimal totalAmount, Member currentMember, boolean hasCoupon) {
        BigDecimal zeroAmount = new BigDecimal(0);
        //判断用户是否有这么多积分
        if (useIntegration.compareTo(currentMember.getIntegration()) > 0) {
            return zeroAmount;
        }
        //根据积分使用规则判断是否可用
        //是否可与优惠券共用
        IntegrationConsumeSetting integrationConsumeSetting = integrationConsumeSettingClient.selectByKey(1L);
        if (hasCoupon && integrationConsumeSetting.getCouponStatus().equals(0)) {
            //不可与优惠券共用
            return zeroAmount;
        }
        //是否达到最低使用积分门槛
        if (useIntegration.compareTo(integrationConsumeSetting.getUseUnit()) < 0) {
            return zeroAmount;
        }
        //是否超过订单抵用最高百分比
        BigDecimal integrationAmount = new BigDecimal(useIntegration).divide(new BigDecimal(integrationConsumeSetting.getUseUnit()), 2, RoundingMode.HALF_EVEN);
        BigDecimal maxPercent = new BigDecimal(integrationConsumeSetting.getMaxPercentPerOrder()).divide(new BigDecimal(100), 2, RoundingMode.HALF_EVEN);
        if (integrationAmount.compareTo(totalAmount.multiply(maxPercent)) > 0) {
            return zeroAmount;
        }
        return integrationAmount;
    }

    /**
     * 对优惠券优惠进行处理
     *
     * @param orderItemList       order_item列表
     * @param couponHistoryDetail 可用优惠券详情
     */
    private void handleCouponAmount(List<OrderItem> orderItemList, CouponHistoryDetail couponHistoryDetail) {
        Coupon coupon = couponHistoryDetail.getCoupon();
        if (coupon.getUseType().equals(0)) {
            //全场通用
            calcPerCouponAmount(orderItemList, coupon);
        } else if (coupon.getUseType().equals(1)) {
            //指定分类
            List<OrderItem> couponOrderItemList = getCouponOrderItemByRelation(couponHistoryDetail, orderItemList, 0);
            calcPerCouponAmount(couponOrderItemList, coupon);
        } else if (coupon.getUseType().equals(2)) {
            //指定商品
            List<OrderItem> couponOrderItemList = getCouponOrderItemByRelation(couponHistoryDetail, orderItemList, 1);
            calcPerCouponAmount(couponOrderItemList, coupon);
        }
    }

    /**
     * 对每个下单商品进行优惠券金额分摊的计算
     *
     * @param orderItemList 可用优惠券的下单商品商品
     */
    private void calcPerCouponAmount(List<OrderItem> orderItemList, Coupon coupon) {
        BigDecimal totalAmount = calcTotalAmount(orderItemList);
        for (OrderItem orderItem : orderItemList) {
            //(商品价格/可用商品总价)*优惠券面额
            BigDecimal couponAmount = orderItem.getProductPrice().divide(totalAmount, 3, RoundingMode.HALF_EVEN).multiply(coupon.getAmount());
            orderItem.setCouponAmount(couponAmount);
        }
    }

    /**
     * 获取与优惠券有关系的下单商品
     *
     * @param couponHistoryDetail 优惠券详情
     * @param orderItemList       下单商品
     * @param type                使用关系类型：0->相关分类；1->指定商品
     */
    private List<OrderItem> getCouponOrderItemByRelation(CouponHistoryDetail couponHistoryDetail, List<OrderItem> orderItemList, int type) {
        List<OrderItem> result = new ArrayList<>();
        if (type == 0) {
            List<Long> categoryIdList = new ArrayList<>();
            for (CouponProductCategoryRelation productCategoryRelation : couponHistoryDetail.getCategoryRelationList()) {
                categoryIdList.add(productCategoryRelation.getProductCategoryId());
            }
            for (OrderItem orderItem : orderItemList) {
                if (categoryIdList.contains(orderItem.getProductCategoryId())) {
                    result.add(orderItem);
                } else {
                    orderItem.setCouponAmount(new BigDecimal(0));
                }
            }
        } else if (type == 1) {
            List<Long> productIdList = new ArrayList<>();
            for (CouponProductRelation productRelation : couponHistoryDetail.getProductRelationList()) {
                productIdList.add(productRelation.getProductId());
            }
            for (OrderItem orderItem : orderItemList) {
                if (productIdList.contains(orderItem.getProductId())) {
                    result.add(orderItem);
                } else {
                    orderItem.setCouponAmount(new BigDecimal(0));
                }
            }
        }
        return result;
    }

    /**
     * 获取该用户可以使用的优惠券
     *
     * @param cartPromotionItemList 购物车优惠列表
     * @param couponId              使用优惠券id
     */
    private CouponHistoryDetail getUseCoupon(List<CartPromotionItem> cartPromotionItemList, Long couponId, Member member) {
        List<CouponHistoryDetail> couponHistoryDetailList = memberCouponClient.listCart(cartPromotionItemList, 1, member).getData();
        for (CouponHistoryDetail couponHistoryDetail : couponHistoryDetailList) {
            if (couponHistoryDetail.getCoupon().getId().equals(couponId)) {
                return couponHistoryDetail;
            }
        }
        return null;
    }

    /**
     * 计算总金额
     */
    private BigDecimal calcTotalAmount(List<OrderItem> orderItemList) {
        BigDecimal totalAmount = new BigDecimal("0");
        for (OrderItem item : orderItemList) {
            totalAmount = totalAmount.add(item.getProductPrice().multiply(new BigDecimal(item.getProductQuantity())));
        }
        return totalAmount;
    }

    /**
     * 锁定下单商品的所有库存
     */
    private void lockStock(List<CartPromotionItem> cartPromotionItemList) {
        for (CartPromotionItem cartPromotionItem : cartPromotionItemList) {
            SkuStock skuStock = skuStockClient.selectByPrimaryKey(cartPromotionItem.getProductSkuId());
            skuStock.setLockStock(skuStock.getLockStock() + cartPromotionItem.getQuantity());
            skuStockClient.updateByPrimaryKeySelective(skuStock);
        }
    }

    /**
     * 判断下单商品是否都有库存
     */
    private boolean hasStock(List<CartPromotionItem> cartPromotionItemList) {
        for (CartPromotionItem cartPromotionItem : cartPromotionItemList) {
            if (cartPromotionItem.getRealStock() == null || cartPromotionItem.getRealStock() <= 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 计算购物车中商品的价格
     */
    private ConfirmOrderResult.CalcAmount calcCartAmount(List<CartPromotionItem> cartPromotionItemList) {
        ConfirmOrderResult.CalcAmount calcAmount = new ConfirmOrderResult.CalcAmount();
        calcAmount.setFreightAmount(new BigDecimal(0));
        BigDecimal totalAmount = new BigDecimal("0");
        BigDecimal promotionAmount = new BigDecimal("0");
        for (CartPromotionItem cartPromotionItem : cartPromotionItemList) {
            totalAmount = totalAmount.add(cartPromotionItem.getPrice().multiply(new BigDecimal(cartPromotionItem.getQuantity())));
            promotionAmount = promotionAmount.add(cartPromotionItem.getReduceAmount().multiply(new BigDecimal(cartPromotionItem.getQuantity())));
        }
        calcAmount.setTotalAmount(totalAmount);
        calcAmount.setPromotionAmount(promotionAmount);
        calcAmount.setPayAmount(totalAmount.subtract(promotionAmount));
        return calcAmount;
    }

}
