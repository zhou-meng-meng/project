package com.example.project.demos.web.dao;

import com.example.project.demos.web.entity.SysRoleEntity;
import com.example.project.demos.web.service.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色信息表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-04-24 13:42:58
 */
@Mapper
public interface SysRoleDao extends BaseMapperPlus<SysRoleDao,SysRoleEntity> {
	
}
