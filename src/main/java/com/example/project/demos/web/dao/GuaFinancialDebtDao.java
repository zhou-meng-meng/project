package com.example.project.demos.web.dao;

import com.example.project.demos.web.entity.GuaFinancialDebtEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 金融报表-融资担保公司资产负债情况表（G3）
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-07-10 09:58:25
 */
@Mapper
public interface GuaFinancialDebtDao extends BaseMapperPlus<GuaFinancialDebtDao,GuaFinancialDebtEntity> {
    int deleteByFillingMonth(@Param(value = "month") String month);
}
