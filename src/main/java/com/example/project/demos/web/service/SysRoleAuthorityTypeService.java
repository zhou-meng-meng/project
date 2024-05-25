package com.example.project.demos.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.project.demos.web.entity.SysRoleAuthorityTypeEntity;

import java.util.List;
import java.util.Map;

/**
 * 角色权限类型表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-25 12:36:42
 */
public interface SysRoleAuthorityTypeService  {

    List<String> queryRoleAuthorityTypeList(String roleId);
    int deleteByRoleId(String roleId);
    boolean insertBatch(String roleId,List<String> list);
}

