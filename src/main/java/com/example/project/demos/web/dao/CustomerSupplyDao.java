package com.example.project.demos.web.dao;

import com.example.project.demos.web.dto.list.CustomerSupplyInfo;
import com.example.project.demos.web.dto.customerSupply.QueryByPageDTO;
import com.example.project.demos.web.entity.CustomerSupplyEntity;
import com.example.project.demos.web.dao.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 供货商客户维护表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-04-24 13:42:58
 */
@Mapper
public interface CustomerSupplyDao extends BaseMapperPlus<CustomerSupplyDao,CustomerSupplyEntity> {
    List<CustomerSupplyInfo> selectCustomerSupplyInfoListByPage(@Param("customer") CustomerSupplyEntity customerSupplyEntity, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param queryByPageDTO 查询条件
     * @return 总行数
     */
    int count(QueryByPageDTO queryByPageDTO);

    CustomerSupplyInfo selectCustomerSupplyInfoById(Long id);
}
