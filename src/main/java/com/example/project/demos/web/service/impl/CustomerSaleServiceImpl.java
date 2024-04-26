package com.example.project.demos.web.service.impl;

import com.example.project.demos.web.dao.CustomerSaleDao;
import com.example.project.demos.web.entity.CustomerSaleEntity;
import com.example.project.demos.web.service.CustomerSaleService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


@Service("customerSaleService")
public class CustomerSaleServiceImpl extends ServiceImpl<CustomerSaleDao, CustomerSaleEntity> implements CustomerSaleService {



}
