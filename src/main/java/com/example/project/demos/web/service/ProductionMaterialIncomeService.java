package com.example.project.demos.web.service;

import com.example.project.demos.web.dto.list.ProductionMaterialIncomeInfo;
import com.example.project.demos.web.dto.list.RawMaterialIncomeInfo;
import com.example.project.demos.web.dto.productionMaterialIncome.*;

import java.net.UnknownHostException;
import java.util.List;

/**
 * 
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-11 09:25:49
 */
public interface ProductionMaterialIncomeService  {

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
    AddOutDTO insert(AddDTO dto) ;

    /**
     * 修改数据
     *
     * @param dto 实例对象
     * @return 实例对象
     */
    EditOutDTO update(EditDTO dto) ;

    /**
     * 通过主键删除数据
     *
     * @param dto 主键
     * @return 是否成功
     */
    DeleteByIdOutDTO deleteById(DeleteByIdDTO dto) ;

    List<ProductionMaterialIncomeInfo> queryListForExport(QueryByPageDTO queryByPageDTO);
}

