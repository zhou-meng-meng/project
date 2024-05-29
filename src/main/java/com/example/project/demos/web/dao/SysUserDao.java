package com.example.project.demos.web.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.project.demos.web.dto.list.SysUserInfo;
import com.example.project.demos.web.dto.sysUser.*;
import com.example.project.demos.web.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 用户信息表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-04-24 13:42:58
 */
@Mapper
public interface SysUserDao extends BaseMapper<SysUserEntity> {
    List<SysUserInfo> selectSysUserInfoListByPage(@Param("user") SysUserEntity SysUserEntity, @Param("pageable") Pageable pageable);

    /**
     * 弹窗使用
     * @param SysUserEntity
     * @param pageable
     * @return
     */
    List<SysUserInfo> selectSysUserInfoPopListByPage(@Param("user") SysUserEntity SysUserEntity, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param queryByPageDTO 查询条件
     * @return 总行数
     */
    int count(QueryByPageDTO queryByPageDTO);

    SysUserInfo selectSysUserInfoById(Long id);

    SysUserInfo selectUserForLogin(@Param(value = "userLogin") String userLogin,@Param(value = "password") String password);

    int selectUserByPwd(@Param(value = "id") Long id,@Param(value = "password") String password);

    List<SysUserEntity> queryUserListByRoleType(@Param(value = "userType") String userType,
                                                @Param(value = "roleType") String roleType,
                                                @Param(value = "deptId") String deptId);

}
