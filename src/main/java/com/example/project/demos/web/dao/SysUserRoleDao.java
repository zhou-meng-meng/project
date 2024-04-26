package com.example.project.demos.web.dao;

import com.example.project.demos.web.entity.SysUserRoleEntity;
import com.example.project.demos.web.service.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户和角色关联表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-04-24 13:42:58
 */
@Mapper
public interface SysUserRoleDao extends BaseMapperPlus<SysUserRoleDao,SysUserRoleEntity> {
	
}
