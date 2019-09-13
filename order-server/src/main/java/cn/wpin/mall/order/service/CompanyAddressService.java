package cn.wpin.mall.order.service;


import cn.wpin.mall.order.entity.CompanyAddress;
import cn.wpin.mall.order.example.CompanyAddressExample;
import cn.wpin.mall.order.mapper.CompanyAddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 收货地址管Service
 * @author wangpin
 */
@Service
public class CompanyAddressService {

    @Autowired
    private CompanyAddressMapper companyAddressMapper;


    /**
     * 获取全部收货地址
     */
    public List<CompanyAddress> list() {
        return companyAddressMapper.selectByExample(new CompanyAddressExample());
    }
}
