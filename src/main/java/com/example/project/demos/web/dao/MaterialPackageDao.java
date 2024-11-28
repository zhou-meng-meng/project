package com.example.project.demos.web.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.project.demos.web.dto.list.MaterialPackageInfo;
import com.example.project.demos.web.dto.materialPackage.*;
import com.example.project.demos.web.entity.MaterialPackageEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 物料产出装袋表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-09 09:26:13
 */
@Mapper
public interface MaterialPackageDao extends BaseMapper<MaterialPackageEntity> {
    List<MaterialPackageInfo> selectMaterialPackageInfoListByPage(@Param("package") QueryByPageDTO dto, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param queryByPageDTO 查询条件
     * @return 总行数
     */
    int count(QueryByPageDTO queryByPageDTO);
    MaterialPackageInfo selectMaterialPackageInfoById(Long id);

}
