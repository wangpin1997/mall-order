package cn.wpin.mall.order.entity;

import cn.wpin.mall.common.entity.IdEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单退货申请
 * @author wangpin 2019-09-09
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("订单退货申请")
public class OrderReturnApply extends IdEntity {


    @ApiModelProperty("订单id")
    private Long orderId;

    @ApiModelProperty("收货地址表id")
    private Long companyAddressId;

    @ApiModelProperty("退货商品id")
    private Long productId;

    @ApiModelProperty("订单编号")
    private String orderSn;

    @ApiModelProperty("申请时间")
    private Date createTime;

    @ApiModelProperty("会员用户名")
    private String memberUsername;

    @ApiModelProperty("退款金额")
    private BigDecimal returnAmount;

    @ApiModelProperty("退货人姓名")
    private String returnName;

    @ApiModelProperty("退货人电话")
    private String returnPhone;

    @ApiModelProperty("申请状态：0->待处理；1->退货中；2->已完成；3->已拒绝")
    private Integer status;

    @ApiModelProperty("处理时间")
    private Date handleTime;

    @ApiModelProperty("商品图片")
    private String productPic;

    @ApiModelProperty("商品名称")
    private String productName;

    @ApiModelProperty("商品品牌")
    private String productBrand;

    @ApiModelProperty("商品销售属性：颜色：红色；尺码：xl;")
    private String productAttr;

    @ApiModelProperty("退货数量")
    private Integer productCount;

    @ApiModelProperty("商品单价")
    private BigDecimal productPrice;

    @ApiModelProperty("商品实际支付单价")
    private BigDecimal productRealPrice;

    @ApiModelProperty("原因")
    private String reason;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("凭证图片，以逗号隔开")
    private String proofPics;

    @ApiModelProperty("处理备注")
    private String handleNote;

    @ApiModelProperty("处理人员")
    private String handleMan;

    @ApiModelProperty("收货人")
    private String receiveMan;

    @ApiModelProperty("收货时间")
    private Date receiveTime;

    @ApiModelProperty("收货备注")
    private String receiveNote;

}