package cn.wpin.mall.order.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 订单修改收货人信息参数
 * @author wangpin
 */
@Getter
@Setter
public class ReceiverInfoParam {

    private Long orderId;
    private String receiverName;
    private String receiverPhone;
    private String receiverPostCode;
    private String receiverDetailAddress;
    private String receiverProvince;
    private String receiverCity;
    private String receiverRegion;
    private Integer status;
}
