package com.example.project.demos.web.service;

import com.example.project.demos.web.dto.list.CustomerAccountRelInfo;
import com.example.project.demos.web.entity.CustomerAccountRelEntity;
import java.util.List;

/**
 * 客户开户账号对应关系表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-04-24 13:42:58
 */
public interface CustomerAccountRelService {

    /**
     * 不分页查询
     */
    List<CustomerAccountRelInfo> queryRelListByCustomerCode(Long customerId);

    int deleteRelByCustomerCode(Long customerId);

    boolean savaBatch(Long customerId,List<CustomerAccountRelEntity>list);

}

