package com.example.project.demos.web.service;

import com.example.project.demos.web.dto.customerPayDetail.*;
import com.example.project.demos.web.dto.list.CustomerPayDetailInfo;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

/**
 * 客户往来账明细
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-10 09:46:07
 */
public interface CustomerPayDetailService  {
    /**
     * 通过ID查询单条数据
     *
     * @param dto 主键
     * @return 实例对象
     */
    //QueryByIdOutDTO queryById(QueryByIdDTO dto);

    /**
     * 分页查询
     *
     * @param dto 筛选条件
     * @return 查询结果
     */
    QueryByPageOutDTO queryByPage(QueryByPageDTO dto);

    /**
     * 新增数据
     * 财务从页面操作使用
     * @param dto 实例对象
     * @return 实例对象
     */
    AddOutDTO insert(AddDTO dto);

    /**
     * 系统自动增加使用
     * @return
     */
    int addPayBySystem(AddPayBySystemDTO dto);

    /**
     * 修改数据
     *
     * @param dto 实例对象
     * @return 实例对象
     */
    EditOutDTO update(EditDTO dto);

    /**
     * 通过主键删除数据
     *
     * @param dto 主键
     * @return 是否成功
     */
    DeleteByIdOutDTO deleteById(DeleteByIdDTO dto);

    List<CustomerPayDetailInfo> queryListForExport(QueryByPageDTO dto);

    UpdateUnitPriceOutDTO updateUnitPrice(UpdateUnitPriceDTO dto);

    EditBookBalanceOutDTO editBookBalance(EditBookBalanceDTO dto);

    void downPayDetailBakZip(ExportPayDetailBakDTO dto,  HttpServletResponse response) throws Exception;

}

