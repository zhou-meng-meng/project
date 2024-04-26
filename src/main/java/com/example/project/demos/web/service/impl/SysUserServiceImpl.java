package com.example.project.demos.web.service.impl;

import com.example.project.demos.web.dao.SysUserDao;
import com.example.project.demos.web.entity.SysUserEntity;
import com.example.project.demos.web.service.SysUserService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {

}
