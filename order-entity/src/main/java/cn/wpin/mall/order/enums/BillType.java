package cn.wpin.mall.order.enums;

import cn.wpin.mall.common.enums.CommonEnums;

/**
 * 发票类型枚举
 * @author wangpin
 */

public enum BillType implements CommonEnums {

    /**
     * 发票类型：0->不开发票；1->电子发票；2->纸质发票
     */
    NOT_BILL(0,"不开发票"),
    ELECTORNIC_BILL(1,"电子发票"),
    PAPER_BILL(2,"纸质发票");



    private int index;

    private String desc;

    BillType(int index, String desc) {
        this.index = index;
        this.desc = desc;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public String getDesc() {
        return desc;
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
