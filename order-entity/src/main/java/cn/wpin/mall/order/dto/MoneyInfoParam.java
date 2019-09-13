package cn.wpin.mall.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 修改订单费用信息参数
 *
 * @author wpin
 * @date 2019-9-13 12:29:10
 */
@Getter
@Setter
public class MoneyInfoParam {

    private Long orderId;
    private BigDecimal freightAmount;
    private BigDecimal discountAmount;
    private Integer status;
}
