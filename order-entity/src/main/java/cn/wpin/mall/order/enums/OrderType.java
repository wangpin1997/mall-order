package cn.wpin.mall.order.enums;

import cn.wpin.mall.common.enums.CommonEnums;

/**
 * 订单类型枚举
 * @author wangpin
 */

public enum  OrderType implements CommonEnums {

    /**
     * 订单类型：0->正常订单；1->秒杀订单
     */
    NORMAL_ORDER(0,"正常订单"),
    SECKILL_ORDER(0,"秒杀订单");

    private int index;

    private String desc;

    OrderType(int index, String desc) {
        this.index = index;
        this.desc = desc;
    }

    @Override
    public int getIndex() {
        return this.index;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }

    @Override
    public void setIndex(int i) {
        this.index=i;
    }

    @Override
    public void setDesc(String s) {
        this.desc=s;
    }
}
