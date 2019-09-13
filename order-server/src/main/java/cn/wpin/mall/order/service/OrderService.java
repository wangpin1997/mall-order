package cn.wpin.mall.order.service;

import cn.wpin.mall.order.dao.OrderDao;
import cn.wpin.mall.order.dao.OrderOperateHistoryDao;
import cn.wpin.mall.order.dto.*;
import cn.wpin.mall.order.entity.Order;
import cn.wpin.mall.order.entity.OrderOperateHistory;
import cn.wpin.mall.order.example.OrderExample;
import cn.wpin.mall.order.mapper.OrderMapper;
import cn.wpin.mall.order.mapper.OrderOperateHistoryMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单管理Service
 * @author wangpin 
 */
@Service
public class OrderService {
    
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderOperateHistoryDao orderOperateHistoryDao;
    @Autowired
    private OrderOperateHistoryMapper orderOperateHistoryMapper;



    public List<Order> list(OrderQueryParam queryParam, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        return orderDao.getList(queryParam);
    }


    public int delivery(List<OrderDeliveryParam> deliveryParamList) {
        //批量发货
        int count = orderDao.delivery(deliveryParamList);
        //添加操作记录
        List<OrderOperateHistory> operateHistoryList = deliveryParamList.stream()
                .map(omsOrderDeliveryParam -> {
                    OrderOperateHistory history = new OrderOperateHistory();
                    history.setOrderId(omsOrderDeliveryParam.getOrderId());
                    history.setCreateTime(new Date());
                    history.setOperateMan("后台管理员");
                    history.setOrderStatus(2);
                    history.setNote("完成发货");
                    return history;
                }).collect(Collectors.toList());
        orderOperateHistoryDao.insertList(operateHistoryList);
        return count;
    }


    public int close(List<Long> ids, String note) {
        Order record = new Order();
        record.setStatus(4);
        OrderExample example = new OrderExample();
        example.createCriteria().andDeleteStatusEqualTo(0).andIdIn(ids);
        int count = orderMapper.updateByExampleSelective(record, example);
        List<OrderOperateHistory> historyList = ids.stream().map(orderId -> {
            OrderOperateHistory history = new OrderOperateHistory();
            history.setOrderId(orderId);
            history.setCreateTime(new Date());
            history.setOperateMan("后台管理员");
            history.setOrderStatus(4);
            history.setNote("订单关闭:"+note);
            return history;
        }).collect(Collectors.toList());
        orderOperateHistoryDao.insertList(historyList);
        return count;
    }

    public int delete(List<Long> ids) {
        Order record = new Order();
        record.setDeleteStatus(1);
        OrderExample example = new OrderExample();
        example.createCriteria().andDeleteStatusEqualTo(0).andIdIn(ids);
        return orderMapper.updateByExampleSelective(record, example);
    }

    public OrderDetail detail(Long id) {
        return orderDao.getDetail(id);
    }


    public int updateReceiverInfo(ReceiverInfoParam receiverInfoParam) {
        Order order = new Order();
        order.setId(receiverInfoParam.getOrderId());
        order.setReceiverName(receiverInfoParam.getReceiverName());
        order.setReceiverPhone(receiverInfoParam.getReceiverPhone());
        order.setReceiverPostCode(receiverInfoParam.getReceiverPostCode());
        order.setReceiverDetailAddress(receiverInfoParam.getReceiverDetailAddress());
        order.setReceiverProvince(receiverInfoParam.getReceiverProvince());
        order.setReceiverCity(receiverInfoParam.getReceiverCity());
        order.setReceiverRegion(receiverInfoParam.getReceiverRegion());
        return commonInsert(order,receiverInfoParam.getOrderId(),receiverInfoParam.getStatus(),"修改费用信息");
    }



    public int updateMoneyInfo(MoneyInfoParam moneyInfoParam) {
        Order order = new Order();
        order.setId(moneyInfoParam.getOrderId());
        order.setFreightAmount(moneyInfoParam.getFreightAmount());
        order.setDiscountAmount(moneyInfoParam.getDiscountAmount());
        return commonInsert(order,moneyInfoParam.getOrderId(),moneyInfoParam.getStatus(),"修改费用信息");
    }


    /**
     * 修改订单备注
     */

    public int updateNote(Long id, String note, Integer status) {
        Order order = new Order();
        order.setId(id);
        order.setNote(note);
        return commonInsert(order,id,status,"修改备注信息:"+note);
    }

    /**
     * 提取公共代码
     * @param order
     * @param orderId
     * @param status
     * @param note
     * @return
     */
    private int commonInsert(Order order, Long orderId, Integer status,String note){
        order.setModifyTime(new Date());
        int count = orderMapper.updateByPrimaryKeySelective(order);
        //插入操作记录
        OrderOperateHistory history = new OrderOperateHistory();
        history.setOrderId(orderId);
        history.setCreateTime(new Date());
        history.setOperateMan(note);
        history.setOrderStatus(status);
        history.setNote("修改收货人信息");
        orderOperateHistoryMapper.insert(history);
        return count;
    }

}
