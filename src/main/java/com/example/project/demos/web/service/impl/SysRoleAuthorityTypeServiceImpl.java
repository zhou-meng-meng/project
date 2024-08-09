package com.example.project.demos.web.service.impl;

import com.example.project.demos.web.dao.SysRoleAuthorityTypeDao;
import com.example.project.demos.web.entity.SysRoleAuthorityTypeEntity;
import com.example.project.demos.web.service.SysRoleAuthorityTypeService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("sysRoleAuthorityTypeService")
public class SysRoleAuthorityTypeServiceImpl  implements SysRoleAuthorityTypeService {

    @Resource
    SysRoleAuthorityTypeDao sysRoleAuthorityTypeDao;
    @Override
    public List<String> queryRoleAuthorityTypeList(String roleId) {
        return sysRoleAuthorityTypeDao.querySysRoleAuthorityTypeListByRoleId(roleId);
    }

    @Override
    public int deleteByRoleId(String roleId) {
        return sysRoleAuthorityTypeDao.deleteByRoleId(roleId);
    }

    @Override
    public boolean insertBatch(String roleId,List<String> list) {
        List<SysRoleAuthorityTypeEntity> entityList = new ArrayList<>();
        for(String type : list){
            SysRoleAuthorityTypeEntity entity = new SysRoleAuthorityTypeEntity();
            entity.setRoleId(roleId);
            entity.setRoleAuthorityType(type);
            entityList.add(entity);
        }
        return sysRoleAuthorityTypeDao.insertBatch(entityList);
    }
}
