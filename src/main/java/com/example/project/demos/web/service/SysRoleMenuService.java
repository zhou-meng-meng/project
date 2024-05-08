package com.example.project.demos.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.project.demos.web.entity.SysRoleMenuEntity;

import java.util.List;
import java.util.Map;

/**
 * 角色和菜单关联表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-04-24 13:42:58
 */
public interface SysRoleMenuService  {
    List<String> queryMenuListByRoleId(String roleId);

}

