package com.example.project.demos.web.service.impl;

import com.example.project.demos.web.dao.SysUserRoleDao;
import com.example.project.demos.web.dto.list.SysUserRoleInfo;
import com.example.project.demos.web.service.SysUserRoleService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;


@Service("sysUserRoleService")
public class SysUserRoleServiceImpl  implements SysUserRoleService {

    @Resource
    private SysUserRoleDao sysUserRoleDao;
    @Override
    public SysUserRoleInfo selectRoleInfoByUserLogin(String userLogin) {
        return sysUserRoleDao.selectRoleInfoByUserLogin(userLogin);
    }
}
