package cn.wpin.mall.order.entity;



import cn.wpin.mall.sale.entity.CartPromotionItem;
import cn.wpin.mall.sale.entity.CouponHistoryDetail;
import cn.wpin.mall.user.entity.IntegrationConsumeSetting;
import cn.wpin.mall.user.entity.MemberReceiveAddress;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 确认单信息封装
 * @author wangpin 
 */
@Data
public class ConfirmOrderResult {
    /**
     * 包含优惠信息的购物车信息
     */
    private List<CartPromotionItem> cartPromotionItemList;
    /**
     * 用户收货地址列表
     */
    private List<MemberReceiveAddress> memberReceiveAddressList;
    /**
     * 用户可用优惠券列表
     */
    private List<CouponHistoryDetail> couponHistoryDetailList;
    /**
     * 积分使用规则
     */
    private IntegrationConsumeSetting integrationConsumeSetting;
    /**
     * 会员持有的积分
     */
    private Integer memberIntegration;
    /**
     * 计算的金额
     */
    private CalcAmount calcAmount;


    @Data
    public static class CalcAmount{
        /**
         * 订单商品总金额
         */
        private BigDecimal totalAmount;
        /**
         * 运费
         */
        private BigDecimal freightAmount;
        /**
         * 活动优惠
         */
        private BigDecimal promotionAmount;
        /**
         * 应付金额
         */
        private BigDecimal payAmount;

    }
}
