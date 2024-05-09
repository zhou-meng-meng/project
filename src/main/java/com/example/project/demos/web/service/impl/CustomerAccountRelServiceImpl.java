package com.example.project.demos.web.service.impl;

import com.example.project.demos.web.dao.CustomerAccountRelDao;
import com.example.project.demos.web.dto.list.CustomerAccountRelInfo;
import com.example.project.demos.web.entity.CustomerAccountRelEntity;
import com.example.project.demos.web.service.CustomerAccountRelService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;


@Service("costomerAccountRelService")
public class CustomerAccountRelServiceImpl implements CustomerAccountRelService {

    @Resource
    CustomerAccountRelDao customerAccountRelDao;

    @Override
    public List<CustomerAccountRelInfo> queryRelListByCustomerCode(String customerCode) {
        return customerAccountRelDao.queryRelListByCustomerCode(customerCode);
    }

    @Override
    public int deleteRelByCustomerCode(String customerCode) {
        return customerAccountRelDao.deleteRelByCustomerCode(customerCode);
    }

    @Override
    public boolean savaBatch(String customerCode, List<CustomerAccountRelEntity> list) {
        for(CustomerAccountRelEntity entity : list){
            entity.setCustomerCode(customerCode);
        }
        return customerAccountRelDao.insertBatch(list);
    }
}
