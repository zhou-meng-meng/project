package com.example.project.demos.web.service;

import com.example.project.demos.web.dto.list.MaterialDosageInfo;
import com.example.project.demos.web.dto.materialDosage.*;

import java.util.List;

/**
 * 物料用量表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-08 17:08:07
 */
public interface MaterialDosageService  {
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
     * @param dto 筛选条件
     * @return 查询结果
     */
    QueryByPageOutDTO queryByPage(QueryByPageDTO dto);

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

    List<MaterialDosageInfo> queryListForExport(QueryByPageDTO dto);

}

