package cn.wpin.mall.order.entity;

import cn.wpin.mall.common.entity.IdEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 退货原因表
 * @author wangpin 2019-09-09
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("退货原因表")
public class OrderReturnReason extends IdEntity {


    @ApiModelProperty("退货类型")
    private String name;

    @ApiModelProperty("sort")
    private Integer sort;

    @ApiModelProperty("状态：0->不启用；1->启用")
    private Integer status;

    @ApiModelProperty("添加时间")
    private Date createTime;


}