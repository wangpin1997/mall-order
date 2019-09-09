package cn.wpin.mall.order.enums;

import cn.wpin.mall.common.enums.CommonEnums;

/**
 * @author wangpin
 */

public enum PayType implements CommonEnums {

    /**
     * 为<b>0</b>为未支付
     */
    NON_PAY(0,"未支付"),
    /**
     * 为<b>1</b>为支付宝支付
     */
    ALIPAY(1,"支付宝"),
    /**
     * 为<b>2</b>为微信支付
     */
    WEIXIN(2,"微信");

    private int index;
    private String desc;

    PayType(int i, String s) {
        this.index=i;
        this.desc=s;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public void setDesc(String desc) {
        this.desc = desc;
    }
}
