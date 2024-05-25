package com.example.project.demos.web.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.project.demos.web.dto.list.SalesCustomerPayInfo;
import com.example.project.demos.web.dto.salesCustomerPay.QueryByPageDTO;
import com.example.project.demos.web.entity.SalesCustomerPayEntity;
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
public interface SalesCustomerPayDao extends BaseMapper<SalesCustomerPayEntity> {

    List<SalesCustomerPayInfo> selectSalesCustomerPayInfoListByPage(@Param("pay") QueryByPageDTO dto, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param queryByPageDTO 查询条件
     * @return 总行数
     */
    int count(QueryByPageDTO queryByPageDTO);

    SalesCustomerPayInfo selectSalesCustomerPayInfoById(Long id);
	
}
