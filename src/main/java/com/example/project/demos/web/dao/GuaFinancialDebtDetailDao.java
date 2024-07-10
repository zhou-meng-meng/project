package com.example.project.demos.web.dao;

import com.example.project.demos.web.entity.GuaFinancialDebtDetailEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-07-10 09:58:25
 */
@Mapper
public interface GuaFinancialDebtDetailDao extends BaseMapperPlus<GuaFinancialDebtDetailDao,GuaFinancialDebtDetailEntity> {
	List<GuaFinancialDebtDetailEntity> queryList(@Param(value = "debtId") Long debtId);
}
