package com.example.project.demos.web.service.impl;

import com.example.project.demos.web.dao.SysMenuDao;
import com.example.project.demos.web.entity.SysMenuEntity;
import com.example.project.demos.web.service.SysMenuService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenuEntity> implements SysMenuService {


}
