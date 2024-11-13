package com.example.project.demos.web.service;

import com.example.project.demos.web.dto.customerPayDetail.UpdateUnitPriceDTO;
import com.example.project.demos.web.dto.list.SupplyCustomerPayInfo;
import com.example.project.demos.web.dto.supplyCustomerPay.*;
import com.example.project.demos.web.dto.sysUser.UserLoginOutDTO;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-25 15:30:37
 */
public interface SupplyCustomerPayService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    QueryByIdOutDTO queryById(Long id);

    /**
     * 分页查询
     *
     * @param dto 筛选条件
     * @return 查询结果
     */
    QueryByPageOutDTO queryByPage(QueryByPageDTO dto);

    List<SupplyCustomerPayInfo> queryListForExport(QueryByPageDTO dto);

    int updateUnitPrice(UpdateUnitPriceDTO dto, Date date, UserLoginOutDTO user);

}

