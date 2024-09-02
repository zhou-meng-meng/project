package com.example.project.demos.web.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
                                                                      @Param("materialName") String materialName,
                                                                      @Param("startDate") String startDate,
                                                                      @Param("endDate") String endDate,
                                                                      @Param("payStartDate") String payStartDate,
                                                                      @Param("payEndDate") String payEndDate,
                                                                      @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param customerCode 查询条件
     * @return 总行数
     */
    int count(@Param("customerCode") String customerCode,
              @Param("customerName") String customerName,
              @Param("materialName") String materialName,
              @Param("startDate") String startDate,
              @Param("endDate") String endDate,
              @Param("payStartDate") String payStartDate,
              @Param("payEndDate") String payEndDate);

    CustomerPayDetailEntity selectLatestPayDetail(@Param("customerCode") String customerCode);

    /**
     * 财务修改打款金额使用
     * @param id
     * @param amount
     * @return
     */
    int reduceBookBalance(@Param(value = "id") Long id, @Param(value = "amount")BigDecimal amount);
    int addBookBalance(@Param(value = "id") Long id, @Param(value = "amount")BigDecimal amount);

    /**
     * 审核员修改单价  同步修改往来账使用
     * @param id
     * @param amount
     * @return
     */
    int reduceBookBalanceByUnitPrice(@Param(value = "id") Long id, @Param(value = "amount")BigDecimal amount);
    int addBookBalanceByUnitPrice(@Param(value = "id") Long id, @Param(value = "amount")BigDecimal amount);


    List<CustomerPayDetailInfo> queryListForExport(@Param("customerCode") String customerCode,
                                                   @Param("customerName") String customerName,
                                                   @Param("materialName") String materialName,
                                                   @Param("startDate") String startDate,
                                                   @Param("endDate") String endDate,
                                                   @Param("payStartDate") String payStartDate,
                                                   @Param("payEndDate") String payEndDate);

    Long selectIdByBusinessId(Long businessId);

}
