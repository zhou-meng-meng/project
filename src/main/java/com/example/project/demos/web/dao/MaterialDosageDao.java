package com.example.project.demos.web.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.project.demos.web.dto.list.MaterialDosageInfo;
import com.example.project.demos.web.dto.list.MaterialDosageTollAmountInfo;
import com.example.project.demos.web.dto.materialDosage.QueryByPageDTO;
import com.example.project.demos.web.entity.MaterialDosageEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 物料用量表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-08 17:08:07
 */
@Mapper
public interface MaterialDosageDao extends BaseMapper<MaterialDosageEntity> {
    List<MaterialDosageInfo> selectMaterialDosageInfoListByPage(@Param("info") QueryByPageDTO dto, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param queryByPageDTO 查询条件
     * @return 总行数
     */
    int count(QueryByPageDTO queryByPageDTO);

    MaterialDosageInfo selectMaterialDosageInfoById(Long id);

    MaterialDosageTollAmountInfo queryTollAmount(QueryByPageDTO queryByPageDTO);

    List<MaterialDosageInfo> queryListForExport(@Param("info") QueryByPageDTO dto);
}
