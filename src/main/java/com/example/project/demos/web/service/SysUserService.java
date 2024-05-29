package com.example.project.demos.web.service;

import com.example.project.demos.web.dto.list.SysUserInfo;
import com.example.project.demos.web.dto.sysUser.*;
import com.example.project.demos.web.entity.SysUserEntity;

import java.util.List;

/**
 * 用户信息表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-04-24 13:42:58
 */
public interface SysUserService {
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    QueryByIdOutDTO queryById(Long id);

    /**
     * 分页查询
     *
     * @param queryByPageDTO 筛选条件
     * @return 查询结果
     */
    QueryByPageOutDTO queryByPage(QueryByPageDTO queryByPageDTO);

    QueryPopByPageOutDTO queryPopByPage(QueryPopByPageDTO queryByPageDTO);

    /**
     * 新增数据
     *
     * @param dto 实例对象
     * @return 实例对象
     */
    AddOutDTO insert(AddDTO dto);

    /**
     * 修改数据
     *
     * @param dto 实例对象
     * @return 实例对象
     */
    EditOutDTO update(EditDTO dto);

    /**
     * 通过主键删除数据
     *
     * @param dto 主键
     * @return 是否成功
     */
    DeleteByIdOutDTO deleteById(DeleteByIdDTO dto);

    UserLoginOutDTO userLogin(UserLoginDTO dto);

    ResetPwdOutDTO restPwd(ResetPwdDTO dto);

    UpdatePwdOutDTO updatePwd(UpdatePwdDTO dto);

    /**
     * 通过用户类型和权限类型获取相关人
     * @param userType
     * @param roleType
     * @return
     */
    List<SysUserEntity> queryUserListByRoleType(String userType, String roleType,String deptId);


}

