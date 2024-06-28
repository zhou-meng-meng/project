package com.example.project.demos.web.dao;

import com.example.project.demos.web.dto.list.MaterialPackageDetailInfo;
import com.example.project.demos.web.entity.MaterialPackageDetailEntity;
import com.example.project.demos.web.dao.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;

/**
 * 物料装袋表-明细
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-09 09:26:13
 */
@Mapper
public interface MaterialPackageDetailDao extends BaseMapperPlus<MaterialPackageDetailDao,MaterialPackageDetailEntity> {
    List<MaterialPackageDetailInfo> selectMaterialPackageDetailInfoList(@Param("packageId") Long packageId );

    int  deleteByPackageId(Long packageId);
}
