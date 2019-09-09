package cn.wpin.mall.order.entity;

import cn.wpin.mall.common.entity.IdEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 订单设置表
 * @author wangpin 2019-09-09
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("订单设置表")
public class OrderSetting extends IdEntity {


    @ApiModelProperty("秒杀订单超时关闭时间(分)")
    private Integer flashOrderOvertime;

    @ApiModelProperty("正常订单超时时间(分)")
    private Integer normalOrderOvertime;

    @ApiModelProperty("发货后自动确认收货时间（天）")
    private Integer confirmOvertime;

    @ApiModelProperty("自动完成交易时间，不能申请售后（天）")
    private Integer finishOvertime;

    @ApiModelProperty("_overtime int(11)")
    private Integer commentOvertime;
}