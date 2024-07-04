package com.example.project.demos.web.service;

import com.example.project.demos.web.dto.customerSale.*;
import com.example.project.demos.web.dto.list.CustomerSaleInfo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 销售客户维护表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-04-24 13:42:58
 */
public interface CustomerSaleService {
    /**
     * 通过ID查询单条数据
     *
     * @param dto 主键
     * @return 实例对象
     */
    QueryByIdOutDTO queryById(QueryByIdDTO dto);

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

    int updateApprove(Long id, String result, String opinion, String userLogin,  Date date) ;

    QueryByPageOutDTO queryPopByPage(QueryByPageDTO queryByPageDTO);

    List<CustomerSaleInfo> queryListForExport(QueryByPageDTO dto);

}

