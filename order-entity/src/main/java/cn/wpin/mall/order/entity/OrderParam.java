package cn.wpin.mall.order.entity;

import cn.wpin.mall.user.entity.Member;
import lombok.Data;

/**
 * 生成订单时传入的参数
 * @author wangpin
 */
@Data
public class OrderParam {
    /**
     * 收货地址id
     */
    private Long memberReceiveAddressId;
    /**
     * 优惠券id
     */
    private Long couponId;
    /**
     * 使用的积分数
     */
    private Integer useIntegration;
    /**
     * 支付方式
     */
    private Integer payType;


    private Member member;

}
