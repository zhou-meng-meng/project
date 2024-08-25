package com.example.project.demos.web.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.project.demos.web.dto.customerSale.QueryByPageDTO;
import com.example.project.demos.web.dto.list.CustomerSaleInfo;
import com.example.project.demos.web.entity.CustomerSaleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 销售客户维护表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-04-24 13:42:58
 */
@Mapper
public interface CustomerSaleDao extends BaseMapper<CustomerSaleEntity> {

    List<CustomerSaleInfo> selectCustomerSaleInfoListByPage(@Param("customer") QueryByPageDTO queryByPageDTO, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param queryByPageDTO 查询条件
     * @return 总行数
     */
    int count(QueryByPageDTO queryByPageDTO);

    CustomerSaleInfo selectCustomerSaleInfoById(Long id);

    List<CustomerSaleInfo> selectPopListByPage(@Param("customer") QueryByPageDTO queryByPageDTO, @Param("pageable") Pageable pageable);
    int countPop( QueryByPageDTO queryByPageDTO);

    List<CustomerSaleInfo> queryListForExport(@Param("customer") QueryByPageDTO queryByPageDTO);

    List<CustomerSaleEntity> queryList();

    String queryMaxCode();

}
