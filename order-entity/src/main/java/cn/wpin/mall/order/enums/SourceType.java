package cn.wpin.mall.order.enums;

import cn.wpin.mall.common.enums.CommonEnums;

/**
 * 订单来源枚举
 * @author wangpin
 */
public enum SourceType implements CommonEnums {

    /**
     * 订单来源：0->pc订单；1->app订单
     */
    PC(0,"pc订单"),
    APP(1,"app订单");


    private int index;

    private String desc;

    SourceType(int index, String desc) {
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
