package com.example.project.demos.web.controller;

import com.example.project.demos.web.dto.customerPayDetail.*;
import com.example.project.demos.web.dto.list.CustomerPayDetailInfo;
import com.example.project.demos.web.service.CustomerPayDetailService;
import com.example.project.demos.web.utils.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 往来账明细(customer_pay_detail)表控制层
 *
 * @author makejava
 * @since 2024-02-26 20:37:53
 */
@RestController
@RequestMapping("customerPayDetail")
@Api(tags="往来账明细维护")
public class CustomerPayDetailController {
    /**
     * 服务对象
     */
    @Resource
    private CustomerPayDetailService customerPayDetailService;

    /**
     * 分页查询
     *
     * @param queryByPageDTO 筛选条件
     * @return 查询结果
     */
    @PostMapping("/queryPageList")
    @ApiOperation("查询列表(分页)")
    public QueryByPageOutDTO queryByPage(@RequestBody QueryByPageDTO queryByPageDTO) {
        QueryByPageOutDTO outDTO = this.customerPayDetailService.queryByPage(queryByPageDTO);
        return outDTO;
    }

    /**
     * 新增数据
     *
     * @param dto 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    @ApiOperation("新增数据")
    public AddOutDTO add(@RequestBody AddDTO dto) {
        AddOutDTO outDTO = customerPayDetailService.insert(dto);
        return outDTO;
    }

    /**
     * 编辑数据
     *
     * @param dto 实体
     * @return 编辑结果
     */
    @PostMapping("/edit")
    @ApiOperation("编辑数据")
    public EditOutDTO edit(@RequestBody EditDTO dto) {
        EditOutDTO outDTO = customerPayDetailService.update(dto);
        return outDTO;
    }

    /**
     * 删除数据
     *
     * @param dto 主键
     * @return 删除是否成功
     */
    @PostMapping("/deleteById")
    @ApiOperation("根据ID删除数据")
    public DeleteByIdOutDTO deleteById(@RequestBody DeleteByIdDTO dto) {
        DeleteByIdOutDTO outDTO = customerPayDetailService.deleteById(dto);
        return outDTO;
    }

    /**
     * 导出销售客户往来账列表
     */
    @PostMapping("/export")
    @ApiOperation("导出往来账明细")
    public void export(@RequestBody QueryByPageDTO dto) {
        List<CustomerPayDetailInfo> list = customerPayDetailService.queryListForExport(dto);
        ExcelUtil.exportExcel(list, "往来账明细列表", CustomerPayDetailInfo.class);
    }

    /**
     * 编辑数据
     *
     * @param dto 实体
     * @return 编辑结果
     */
    @PostMapping("/updateUnitPrice")
    @ApiOperation("修改审核后单价")
    public UpdateUnitPriceOutDTO edit(@RequestBody UpdateUnitPriceDTO dto) {
        UpdateUnitPriceOutDTO outDTO = customerPayDetailService.updateUnitPrice(dto);
        return outDTO;
    }

    /**
     * 账面余额维护
     *
     * @param dto 实体
     * @return 账面余额维护结果
     */
    @PostMapping("/editBookBalance")
    @ApiOperation("账面余额维护")
    public EditBookBalanceOutDTO editBookBalance(@RequestBody EditBookBalanceDTO dto) {
        EditBookBalanceOutDTO outDTO = customerPayDetailService.editBookBalance(dto);
        return outDTO;
    }

    @PostMapping("/exportPayDetailBak")
    @ApiOperation("导出客户往来账明细备份")
    public void exportPayDetailBak(@RequestBody ExportPayDetailBakDTO dto, HttpServletRequest request, HttpServletResponse response ) throws Exception {
        customerPayDetailService.downPoliceZip(dto, request,  response);
    }



}

