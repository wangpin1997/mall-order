package cn.wpin.mall.order.entity;

import cn.wpin.mall.common.entity.IdEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 公司收发货地址表
 * @author wangpin 2019-09-09
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("公司收发货地址表")
public class CompanyAddress extends IdEntity {

    @ApiModelProperty("地址名称")
    private String addressName;

    @ApiModelProperty("默认发货地址：0->否；1->是")
    private Integer sendStatus;

    @ApiModelProperty("是否默认收货地址：0->否；1->是")
    private Integer receiveStatus;

    @ApiModelProperty("收发货人姓名")
    private String name;

    @ApiModelProperty("收货人电话")
    private String phone;

    @ApiModelProperty("省/直辖市")
    private String province;

    @ApiModelProperty("市")
    private String city;

    @ApiModelProperty("区")
    private String region;

    @ApiModelProperty("详细地址")
    private String detailAddress;

}