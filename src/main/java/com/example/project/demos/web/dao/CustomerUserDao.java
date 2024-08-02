package com.example.project.demos.web.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.project.demos.web.dto.customerUser.QueryByPageDTO;
import com.example.project.demos.web.entity.CustomerUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 客户用户表(CustomerUser)表数据库访问层
 *
 * @author makejava
 * @since 2024-02-26 20:37:53
 */
public interface CustomerUserDao extends BaseMapper<CustomerUser> {

    /**
     * 查询指定行数据
     *
     * @return 对象列表
     */
    //Page<CustomerUserInfo> queryAllByLimit(@Param("page") Page<CustomerUserInfo> pageParam, @Param(value = "user") CustomerUser user);

    List<CustomerUser> queryAllByLimit(@Param("user") CustomerUser customerUser, @Param("pageable") Pageable pageable);

    //Page<CustomerUserInfo> queryAllByLimit(@Param("page") Page<CustomerUserInfo> pageParam, @Param(value = "user") CustomerUser user);

    /**
     * 统计总行数
     *
     * @param queryByPageDTO 查询条件
     * @return 总行数
     */
    long count(QueryByPageDTO queryByPageDTO);



}

