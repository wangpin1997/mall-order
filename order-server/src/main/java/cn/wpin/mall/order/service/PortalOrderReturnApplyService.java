package cn.wpin.mall.order.service;

import cn.wpin.mall.order.entity.OrderReturnApply;
import cn.wpin.mall.order.mapper.OrderReturnApplyMapper;
import cn.wpin.mall.portal.entity.OrderReturnApplyParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 订单退货管理Service实现类
 * @author wangpin
 */
@Service
public class PortalOrderReturnApplyService {


    @Autowired
    private OrderReturnApplyMapper returnApplyMapper;


    public int create(OrderReturnApplyParam returnApply) {
        OrderReturnApply realApply = new OrderReturnApply();
        BeanUtils.copyProperties(returnApply,realApply);
        realApply.setCreateTime(new Date());
        realApply.setStatus(0);
        return returnApplyMapper.insert(realApply);
    }
}
