package com.example.project.demos.web.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.example.project.demos.web.dao.CustomerAccountRelDao;
import com.example.project.demos.web.dto.list.CustomerAccountRelInfo;
import com.example.project.demos.web.entity.CustomerAccountRelEntity;
import com.example.project.demos.web.service.CustomerAccountRelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;


@Slf4j
@Service("customerAccountRelService")
public class CustomerAccountRelServiceImpl implements CustomerAccountRelService {

    @Resource
    CustomerAccountRelDao customerAccountRelDao;

    @Override
    public List<CustomerAccountRelInfo> queryRelListByCustomerCode(Long customerId) {
        return customerAccountRelDao.queryRelListByCustomerCode(customerId);
    }

    @Override
    public int deleteRelByCustomerCode(Long customerId) {
        return customerAccountRelDao.deleteRelByCustomerCode(customerId);
    }

    @Override
    public boolean savaBatch(Long customerId, List<CustomerAccountRelEntity> list) {
        if(CollectionUtil.isNotEmpty(list) && list.size() > 0){
            for(CustomerAccountRelEntity entity : list){
                entity.setCustomerId(customerId);
            }
            return customerAccountRelDao.insertBatch(list);
        }else{
            log.info("账户列表为空");
            return true;
        }

    }
}
