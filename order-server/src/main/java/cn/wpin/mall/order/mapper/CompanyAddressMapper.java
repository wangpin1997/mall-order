package cn.wpin.mall.order.mapper;

import cn.wpin.mall.order.entity.CompanyAddress;
import cn.wpin.mall.order.example.CompanyAddressExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CompanyAddressMapper {
    long countByExample(CompanyAddressExample example);

    int deleteByExample(CompanyAddressExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CompanyAddress record);

    int insertSelective(CompanyAddress record);

    List<CompanyAddress> selectByExample(CompanyAddressExample example);

    CompanyAddress selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CompanyAddress record, @Param("example") CompanyAddressExample example);

    int updateByExample(@Param("record") CompanyAddress record, @Param("example") CompanyAddressExample example);

    int updateByPrimaryKeySelective(CompanyAddress record);

    int updateByPrimaryKey(CompanyAddress record);
}