package com.example.project.demos.web.dao;

import com.example.project.demos.web.dto.list.RawMaterialIncomeInfo;
import com.example.project.demos.web.dto.rawMaterialIncome.QueryByPageDTO;
import com.example.project.demos.web.entity.RawMaterialIncomeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

/**
 * 原材料来料入库表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-10 14:54:43
 */
@Mapper
public interface RawMaterialIncomeDao extends BaseMapper<RawMaterialIncomeEntity> {
    List<RawMaterialIncomeInfo> selectRawMaterialIncomeInfoListByPage(@Param("raw") QueryByPageDTO dto, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param queryByPageDTO 查询条件
     * @return 总行数
     */
    int count(QueryByPageDTO queryByPageDTO);

    RawMaterialIncomeInfo selectRawMaterialIncomeInfoById(Long id);

    BigDecimal getAvgUnitPriceOfMaterial(@Param("materialCode") String materialCode);

    List<RawMaterialIncomeInfo> queryListForExport(@Param("raw") QueryByPageDTO dto);
	
}
