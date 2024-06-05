package com.example.project.demos.web.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.project.demos.web.dto.customerPayDetail.QueryByPageDTO;
import com.example.project.demos.web.dto.list.CustomerPayDetailInfo;
import com.example.project.demos.web.entity.CustomerPayDetailEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

/**
 * 客户往来账明细
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-10 09:46:07
 */
@Mapper
public interface CustomerPayDetailDao extends BaseMapper<CustomerPayDetailEntity> {

    List<CustomerPayDetailInfo> selectCustomerPayDetailInfoListByPage(@Param("customerCode") String customerCode,
                                                                      @Param("customerName") String customerName,
                                                                      @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param customerCode 查询条件
     * @return 总行数
     */
    int count(@Param("customerCode") String customerCode);

    CustomerPayDetailEntity selectLatestPayDetail(@Param("customerCode") String customerCode);

    int reduceBookBalance(@Param(value = "id") Long id, @Param(value = "amount")BigDecimal amount);
    int addBookBalance(@Param(value = "id") Long id, @Param(value = "amount")BigDecimal amount);

}
