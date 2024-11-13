package com.example.project.demos.web.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.project.demos.web.dto.list.CustomerPayInfo;
import com.example.project.demos.web.dto.list.SupplyCustomerPayInfo;
import com.example.project.demos.web.dto.supplyCustomerPay.QueryByPageDTO;
import com.example.project.demos.web.entity.SupplyCustomerPayEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-25 15:30:37
 */
@Mapper
public interface SupplyCustomerPayDao extends BaseMapper<SupplyCustomerPayEntity> {
    List<SupplyCustomerPayInfo> selectSupplyCustomerPayInfoListByPage(@Param("pay") QueryByPageDTO dto, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param queryByPageDTO 查询条件
     * @return 总行数
     */
    int count(QueryByPageDTO queryByPageDTO);

    SupplyCustomerPayInfo selectSupplyCustomerPayInfoById(Long id);

    List<SupplyCustomerPayInfo> queryListForExport(@Param("pay") QueryByPageDTO dto);

    SupplyCustomerPayEntity selectByIncomeId(Long incomeId);

    int updateShowFlagByCustomerCode(@Param("showFlag") String showFlag,
                                     @Param("updateBy") String updateBy,
                                     @Param("customerCode") String customerCode);

    List<CustomerPayInfo> querySupplyCustomer();
	
}
