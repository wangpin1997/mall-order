package cn.wpin.mall.order.enums;

import cn.wpin.mall.common.enums.CommonEnums;

/**
 * @author wangpin
 * 订单状态
 */
public enum OrderStatus implements CommonEnums {

    /**
     * 订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单
     */
    WAIT_PAY(0,"待付款"),
    WAIT_DELIVERY(1,"待发货"),
    ALREADLY_DELIVERY(2,"已发货"),
    ALREADLY_COMPLETE(3,"已完成"),
    ALREADLY_CLOSE(4,"已关闭"),
    INVALID_ORDER(5,"无效订单");


    private int index;

    private String desc;

    OrderStatus(int index, String desc) {
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
