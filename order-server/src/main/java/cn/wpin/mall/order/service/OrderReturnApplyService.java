package cn.wpin.mall.order.service;


import cn.wpin.mall.order.dao.OrderReturnApplyDao;
import cn.wpin.mall.order.dto.OrderReturnApplyResult;
import cn.wpin.mall.order.dto.ReturnApplyQueryParam;
import cn.wpin.mall.order.dto.UpdateStatusParam;
import cn.wpin.mall.order.entity.OrderReturnApply;
import cn.wpin.mall.order.example.OrderReturnApplyExample;
import cn.wpin.mall.order.mapper.OrderReturnApplyMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 退货申请管理Service
 * @author wangpin
 */
@Service
public class OrderReturnApplyService {

    @Autowired
    private OrderReturnApplyDao returnApplyDao;
    @Autowired
    private OrderReturnApplyMapper returnApplyMapper;


    /**
     * 分页查询申请
     */
    public List<OrderReturnApply> list(ReturnApplyQueryParam queryParam, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        return returnApplyDao.getList(queryParam);
    }

    /**
     * 批量删除申请
     */
    public int delete(List<Long> ids) {
        OrderReturnApplyExample example = new OrderReturnApplyExample();
        example.createCriteria().andIdIn(ids).andStatusEqualTo(3);
        return returnApplyMapper.deleteByExample(example);
    }

    /**
     * 修改申请状态
     */
    public int updateStatus(Long id, UpdateStatusParam statusParam) {
        Integer status = statusParam.getStatus();
        OrderReturnApply returnApply = new OrderReturnApply();
        if(status.equals(1)){
            //确认退货
            returnApply.setId(id);
            returnApply.setStatus(1);
            returnApply.setReturnAmount(statusParam.getReturnAmount());
            returnApply.setCompanyAddressId(statusParam.getCompanyAddressId());
            returnApply.setHandleTime(new Date());
            returnApply.setHandleMan(statusParam.getHandleMan());
            returnApply.setHandleNote(statusParam.getHandleNote());
        }else if(status.equals(2)){
            //完成退货
            returnApply.setId(id);
            returnApply.setStatus(2);
            returnApply.setReceiveTime(new Date());
            returnApply.setReceiveMan(statusParam.getReceiveMan());
            returnApply.setReceiveNote(statusParam.getReceiveNote());
        }else if(status.equals(3)){
            //拒绝退货
            returnApply.setId(id);
            returnApply.setStatus(3);
            returnApply.setHandleTime(new Date());
            returnApply.setHandleMan(statusParam.getHandleMan());
            returnApply.setHandleNote(statusParam.getHandleNote());
        }else{
            return 0;
        }
        return returnApplyMapper.updateByPrimaryKeySelective(returnApply);
    }

    /**
     * 获取指定申请详情
     */
    public OrderReturnApplyResult getItem(Long id) {
        return returnApplyDao.getDetail(id);
    }



}
