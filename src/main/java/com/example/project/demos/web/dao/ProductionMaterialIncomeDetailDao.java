package com.example.project.demos.web.dao;

import com.example.project.demos.web.dto.list.ProductProducerInfo;
import com.example.project.demos.web.entity.ProductionMaterialIncomeDetailEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-11 09:25:49
 */
@Mapper
public interface ProductionMaterialIncomeDetailDao extends BaseMapperPlus<ProductionMaterialIncomeDetailDao,ProductionMaterialIncomeDetailEntity> {
    List<ProductProducerInfo> selectProductProducerInfoList(@Param(value = "incomeId") Long incomeId,@Param(value = "producerName") String producerName);

    int deleteByIncomeId(Long incomeId);
}
