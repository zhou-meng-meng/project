package com.example.project.demos.web.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.project.demos.web.dto.list.SysUserRoleInfo;
import com.example.project.demos.web.entity.SysUserRoleEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户和角色关联表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-04-24 13:42:58
 */
@Mapper
public interface SysUserRoleDao extends BaseMapper<SysUserRoleEntity> {
    SysUserRoleInfo selectRoleInfoByUserLogin(String userLogin);
}
