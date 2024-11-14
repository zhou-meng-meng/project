package com.example.project.demos.web.controller;

import com.example.project.demos.web.dto.list.SalesCustomerPayInfo;
import com.example.project.demos.web.dto.salesCustomerPay.*;
import com.example.project.demos.web.service.SalesCustomerPayService;
import com.example.project.demos.web.utils.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.List;

/**
 * 销售方往来账(sales_customer_pay)表控制层
 *
 * @author makejava
 * @since 2024-02-26 20:37:53
 */
@RestController
@RequestMapping("salesCustomerPay")
@Api(tags="销售客户往来账维护")
public class SalesCustomerPayController {
    /**
     * 服务对象
     */
    @Resource
    private SalesCustomerPayService salesCustomerPayService;

    /**
     * 分页查询
     *
     * @param queryByPageDTO 筛选条件
     * @return 查询结果
     */
    @PostMapping("/queryPageList")
    @ApiOperation("查询列表(分页)")
    public QueryByPageOutDTO queryByPage(@RequestBody QueryByPageDTO queryByPageDTO) {
        QueryByPageOutDTO outDTO = this.salesCustomerPayService.queryByPage(queryByPageDTO);
        return outDTO;
    }

    /**
     * 通过主键查询单条数据
     *
     * @param dto 主键
     * @return 单条数据
     */
    @PostMapping("/queryById")
    @ApiOperation("通过主键查询单条数据")
    public QueryByIdOutDTO queryById(@RequestBody QueryByIdDTO dto) {
        QueryByIdOutDTO outDTO = this.salesCustomerPayService.queryById(dto.getId());
        return outDTO;
    }

    /**
     * 导出销售客户往来账列表
     */
    @PostMapping("/export")
    @ApiOperation("导出销售客户往来账列表")
    public void export(@RequestBody QueryByPageDTO dto) {
        List<SalesCustomerPayInfo> list = salesCustomerPayService.queryListForExport(dto);
        ExcelUtil.exportExcel(list, "销售客户往来账列表", SalesCustomerPayInfo.class);
    }

}

