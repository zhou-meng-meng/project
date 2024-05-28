package com.example.project.demos.web.service;

import com.example.project.demos.web.dto.rawMaterialIncome.*;

import java.math.BigDecimal;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * 原材料来料入库表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-10 14:54:43
 */
public interface RawMaterialIncomeService  {
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

    /**
     * 新增数据
     *
     * @param dto 实例对象
     * @return 实例对象
     */
    AddOutDTO insert(AddDTO dto) throws UnknownHostException;

    /**
     * 修改数据
     *
     * @param dto 实例对象
     * @return 实例对象
     */
    EditOutDTO update(EditDTO dto) throws UnknownHostException;

    /**
     * 通过主键删除数据
     *
     * @param dto 主键
     * @return 是否成功
     */
    DeleteByIdOutDTO deleteById(DeleteByIdDTO dto) throws UnknownHostException;


    int updateApprove(Long id, String result, String opinion, String userLogin, BigDecimal unitPrice, BigDecimal tollAmount, Date date) throws UnknownHostException;


}

