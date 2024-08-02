package com.example.project.demos.web.dao;

import com.example.project.demos.web.dto.list.CustomerAccountRelInfo;
import com.example.project.demos.web.entity.CustomerAccountRelEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 客户开户账号对应关系表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-04-24 13:42:58
 */
@Mapper
public interface CustomerAccountRelDao extends BaseMapperPlus<CustomerAccountRelDao, CustomerAccountRelEntity> {
    List<CustomerAccountRelInfo> queryRelListByCustomerCode(Long customerId);

    int deleteRelByCustomerCode(Long customerId);

}
