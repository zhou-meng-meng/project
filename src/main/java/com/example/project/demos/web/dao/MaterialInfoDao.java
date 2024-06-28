package com.example.project.demos.web.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.project.demos.web.dto.list.MaterialInfo;
import com.example.project.demos.web.dto.materialInfo.*;
import com.example.project.demos.web.entity.MaterialInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 物料信息维护表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-08 15:30:25
 */
@Mapper
public interface MaterialInfoDao extends BaseMapper<MaterialInfoEntity> {
    List<MaterialInfo> selectMaterialInfoListByPage(@Param("info") MaterialInfoEntity materialInfo,@Param("supplyerName") String supplyerName, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param queryByPageDTO 查询条件
     * @return 总行数
     */
    int count(QueryByPageDTO queryByPageDTO);

    MaterialInfo selectMaterialInfoById(Long id);
    MaterialInfo selectMaterialInfoByCode(String code);

    List<MaterialInfo> queryListForExport(@Param("info") MaterialInfoEntity materialInfo);
}
