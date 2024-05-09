package com.example.project.demos.web.service;

import com.example.project.demos.web.dto.list.MaterialPackageDetailInfo;
import com.example.project.demos.web.dto.materialPackageDetail.*;

import java.util.List;

/**
 * 物料装袋表-明细
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-09 09:26:13
 */
public interface MaterialPackageDetailService  {
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    //QueryByIdOutDTO queryById(Long id);

    /**
     * 分页查询
     *
     * @return 查询结果

    //QueryByPageOutDTO queryByPage(QueryByPageDTO queryByPageDTO);
     */

    //不分页查询
    List<MaterialPackageDetailInfo> queryByPackageId(Long packageId);

    /**
     * 新增数据
     *
     * @param dto 实例对象
     * @return 实例对象
     */
    //AddOutDTO insert(AddDTO dto);

    /**
     * 修改数据
     *
     * @param dto 实例对象
     * @return 实例对象
     */
    //EditOutDTO update(EditDTO dto);

    /**
     * 通过主键删除数据
     *
     * @param dto 主键
     * @return 是否成功
     */
    //DeleteByIdOutDTO deleteById(DeleteByIdDTO dto);

    int deleteByPackageId(Long packageId);

    boolean insertBatch(Long packageId, List<MaterialPackageDetailInfo> list);
}

