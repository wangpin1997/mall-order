package cn.wpin.mall.order.entity;

import cn.wpin.mall.common.entity.IdEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 订单操作历史记录
 * @author 大狼狗 2019-09-09
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("订单操作历史记录")
public class OrderOperateHistory extends IdEntity {

    @ApiModelProperty("订单id")
    private Long orderId;

    @ApiModelProperty("操作人：用户；系统；后台管理员")
    private String operateMan;

    @ApiModelProperty("操作时间")
    private Date createTime;

    @ApiModelProperty("订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单")
    private Integer orderStatus;

    @ApiModelProperty("备注")
    private String note;

}