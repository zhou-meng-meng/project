package com.example.project.demos.web.service;

import com.example.project.demos.web.dto.customerUser.QueryByPageDTO;
import com.example.project.demos.web.dto.customerUser.QueryByPageOutDTO;
import com.example.project.demos.web.entity.CustomerUser;
import org.springframework.data.domain.Page;

/**
 * 客户用户表(CustomerUser)表服务接口
 *
 * @author makejava
 * @since 2024-02-26 20:37:53
 */
public interface CustomerUserService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    CustomerUser queryById(Long id);

    /**
     * 分页查询
     *
     * @param queryByPageDTO 筛选条件
     * @return 查询结果
     */
    QueryByPageOutDTO queryByPage(QueryByPageDTO queryByPageDTO);

    /**
     * 新增数据
     *
     * @param customerUser 实例对象
     * @return 实例对象
     */
    boolean insert(CustomerUser customerUser);

    /**
     * 修改数据
     *
     * @param customerUser 实例对象
     * @return 实例对象
     */
    boolean update(CustomerUser customerUser);

    /**
     * 通过主键删除数据
     *
     * @param customerId 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
