package com.example.project.demos.web.service.impl;

import com.example.project.demos.web.dao.SysRoleDao;
import com.example.project.demos.web.entity.SysRoleEntity;
import com.example.project.demos.web.service.SysRoleService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;



@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRoleEntity> implements SysRoleService {


}
