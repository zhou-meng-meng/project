package com.example.project.demos.web.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.project.demos.web.entity.SysRoleAuthorityTypeEntity;
import com.example.project.demos.web.dao.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 角色权限类型表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-25 12:36:42
 */
@Mapper
public interface SysRoleAuthorityTypeDao extends BaseMapperPlus<SysRoleAuthorityTypeDao,SysRoleAuthorityTypeEntity> {
	List<String> querySysRoleAuthorityTypeListByRoleId(@Param(value = "roleId") String roleId);

    int deleteByRoleId(@Param(value = "roleId") String roleId);
}
