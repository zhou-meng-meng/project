package com.example.project.demos.web.dao;

import com.example.project.demos.web.dto.list.ProductionMaterialIncomeInfo;
import com.example.project.demos.web.dto.productionMaterialIncome.QueryByPageDTO;
import com.example.project.demos.web.entity.ProductionMaterialIncomeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-11 09:25:49
 */
@Mapper
public interface ProductionMaterialIncomeDao extends BaseMapper<ProductionMaterialIncomeEntity> {
    List<ProductionMaterialIncomeInfo> selectProductionMaterialIncomeInfoListByPage(@Param("idList") List<Long> idList, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param dto 查询条件
     * @return 总行数
     */
    int count(QueryByPageDTO dto);

    List<Long> queryIdList(QueryByPageDTO dto);

    ProductionMaterialIncomeInfo selectProductionMaterialIncomeInfoById(Long id);

    List<ProductionMaterialIncomeInfo> queryListForExport(@Param("idList") List<Long> idList);

    List<String> queryBillNoListByParam(@Param("billNoPrefix") String billNoPrefix);
}
