package cn.wpin.mall.order.entity;

import cn.wpin.mall.common.entity.IdEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 订单中所包含的商品
 * @author wangpin
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("订单中所包含的商品")
public class OrderItem extends IdEntity {

    @ApiModelProperty("订单id")
    private Long orderId;

    @ApiModelProperty("订单编号")
    private String orderSn;

    @ApiModelProperty("product_id")
    private Long productId;

    @ApiModelProperty("product_pic")
    private String productPic;

    @ApiModelProperty("product_name")
    private String productName;

    @ApiModelProperty("product_brand")
    private String productBrand;

    @ApiModelProperty("product_sn")
    private String productSn;

    @ApiModelProperty("销售价格")
    private BigDecimal productPrice;

    @ApiModelProperty("购买数量")
    private Integer productQuantity;

    @ApiModelProperty("商品sku编号")
    private Long productSkuId;

    @ApiModelProperty("商品sku条码")
    private String productSkuCode;

    @ApiModelProperty("商品分类id")
    private Long productCategoryId;

    @ApiModelProperty("商品的销售属性")
    private String sp1;

    @ApiModelProperty("sp2")
    private String sp2;

    @ApiModelProperty("sp3")
    private String sp3;

    @ApiModelProperty("商品促销名称")
    private String promotionName;

    @ApiModelProperty("商品促销分解金额")
    private BigDecimal promotionAmount;

    @ApiModelProperty("优惠券优惠分解金额")
    private BigDecimal couponAmount;

    @ApiModelProperty("积分优惠分解金额")
    private BigDecimal integrationAmount;

    @ApiModelProperty("该商品经过优惠后的分解金额")
    private BigDecimal realAmount;

    @ApiModelProperty("gift_integration")
    private Integer giftIntegration;

    @ApiModelProperty("gift_growth")
    private Integer giftGrowth;

    @ApiModelProperty("商品销售属性:[{ key : 颜色 ， value : 颜色 }，{ key : 容量 ， value : 4g }]")
    private String productAttr;


}