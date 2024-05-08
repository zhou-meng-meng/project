package com.example.project.demos.web.service.impl;

import com.example.project.demos.web.dao.SysRoleMenuDao;
import com.example.project.demos.web.entity.SysRoleMenuEntity;
import com.example.project.demos.web.service.SysRoleMenuService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.List;


@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl  implements SysRoleMenuService {

    @Resource
    SysRoleMenuDao sysRoleMenuDao;
    @Override
    public List<String> queryMenuListByRoleId(String roleId) {
        return sysRoleMenuDao.queryMenuListByRoleId(roleId);
    }
}
