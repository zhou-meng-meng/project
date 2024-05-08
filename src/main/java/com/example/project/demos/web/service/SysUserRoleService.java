package com.example.project.demos.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.project.demos.web.dto.list.SysUserRoleInfo;
import com.example.project.demos.web.entity.SysUserRoleEntity;

import java.util.Map;

/**
 * 用户和角色关联表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-04-24 13:42:58
 */
public interface SysUserRoleService  {
    SysUserRoleInfo selectRoleInfoByUserLogin(String userLogin);
}

