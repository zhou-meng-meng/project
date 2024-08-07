package com.example.project.demos.web.service;


import com.example.project.demos.web.dto.list.RebuildInboundInfo;
import com.example.project.demos.web.dto.rebuildInbound.*;

import java.util.List;

/**
 * 
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-11 11:13:27
 */
public interface RebuildInboundService {

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

    List<RebuildInboundInfo> queryListForExport(QueryByPageDTO dto);
}

