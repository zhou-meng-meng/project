package com.example.project.demos.web.service.impl;

import com.example.project.demos.web.dao.SysRoleMenuDao;
import com.example.project.demos.web.entity.SysRoleMenuEntity;
import com.example.project.demos.web.service.SysRoleMenuService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl  implements SysRoleMenuService {

    @Resource
    SysRoleMenuDao sysRoleMenuDao;
    @Override
    public List<String> queryMenuListByRoleId(String roleId) {
        return sysRoleMenuDao.queryMenuListByRoleId(roleId);
    }

    @Override
    public boolean insertBatch(String roleId, List<String> menuList) {
        List<SysRoleMenuEntity> list = new ArrayList<>();
        for(String menuId: menuList){
            SysRoleMenuEntity entity = new SysRoleMenuEntity();
            entity.setRoleId(roleId);
            entity.setMenuId(menuId);
            list.add(entity);
        }
        return sysRoleMenuDao.insertBatch(list) ;
    }

    @Override
    public int deleteByRoleId(String roleId) {
        return sysRoleMenuDao.deleteByRoleId(roleId);
    }
}
