package com.example.project.demos.web.service.impl;

import com.example.project.demos.web.dao.SysFactoryDao;
import com.example.project.demos.web.entity.SysFactoryEntity;
import com.example.project.demos.web.service.SysFactoryService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;



@Service("sysFactoryService")
public class SysFactoryServiceImpl extends ServiceImpl<SysFactoryDao, SysFactoryEntity> implements SysFactoryService {


}
