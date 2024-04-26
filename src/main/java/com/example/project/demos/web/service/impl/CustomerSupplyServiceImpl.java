package com.example.project.demos.web.service.impl;

import com.example.project.demos.web.dao.CustomerSupplyDao;
import com.example.project.demos.web.entity.CustomerSupplyEntity;
import com.example.project.demos.web.service.CustomerSupplyService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service("customerSupplyService")
public class CustomerSupplyServiceImpl extends ServiceImpl<CustomerSupplyDao, CustomerSupplyEntity> implements CustomerSupplyService {


}
