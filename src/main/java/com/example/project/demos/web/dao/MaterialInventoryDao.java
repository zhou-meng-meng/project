package com.example.project.demos.web.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.project.demos.web.dto.list.MaterialInventoryInfo;
import com.example.project.demos.web.dto.materialInventory.QueryByPageDTO;
import com.example.project.demos.web.dto.materialInventory.QueryByPagePopDTO;
import com.example.project.demos.web.entity.MaterialInventoryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

/**
 * 
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-23 14:35:27
 */
@Mapper
public interface MaterialInventoryDao extends BaseMapper<MaterialInventoryEntity> {
    List<String> selectMaterialCodeByPage (@Param("stock") QueryByPageDTO dto, @Param("pageable") Pageable pageable);

    int countMaterialCode(QueryByPageDTO dto);

    List<MaterialInventoryInfo> selectMaterialInventoryList(@Param(value = "codeList") List<String> codeList);

    int checkIfMaterialCodeExist(@Param("materialCode")String materialCode, @Param("code")String code);

    int addStockInventory(@Param("materialCode")String materialCode, @Param("code")String code, @Param("num")BigDecimal num);
    int reduceStockInventory(@Param("materialCode")String materialCode, @Param("code")String code, @Param("num")BigDecimal num);
    int countPop(QueryByPagePopDTO dto);
    List<MaterialInventoryInfo> selectMaterialByPagePop (@Param("stock") QueryByPagePopDTO dto, @Param("pageable") Pageable pageable);

}
