package cn.wpin.mall.order.dto;

import cn.wpin.mall.order.entity.CompanyAddress;
import cn.wpin.mall.order.entity.OrderReturnApply;

import lombok.Getter;
import lombok.Setter;

/**
 * 申请信息封装
 * @author wangpin
 */
public class OrderReturnApplyResult extends OrderReturnApply {

    @Getter
    @Setter
    private CompanyAddress companyAddress;
}
