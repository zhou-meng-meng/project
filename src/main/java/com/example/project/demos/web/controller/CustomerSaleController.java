package com.example.project.demos.web.controller;

import com.example.project.demos.web.dto.customerSale.*;
import com.example.project.demos.web.dto.list.CustomerSaleInfo;
import com.example.project.demos.web.dto.list.CustomerSupplyInfo;
import com.example.project.demos.web.service.CustomerSaleService;
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
 * 销售客户维护表(customer_sale)表控制层
 *
 * @author makejava
 * @since 2024-02-26 20:37:53
 */
@RestController
@RequestMapping("customerSale")
@Api(tags="销售客户维护表")
public class CustomerSaleController {
    /**
     * 服务对象
     */
    @Resource
    private CustomerSaleService customerSaleService;

    /**
     * 分页查询
     *
     * @param queryByPageDTO 筛选条件
     * @return 查询结果
     */
    @PostMapping("/queryCustomerSalePageList")
    @ApiOperation("查询列表(分页)")
    public QueryByPageOutDTO queryByPage(@RequestBody QueryByPageDTO queryByPageDTO) {
        QueryByPageOutDTO outDTO = this.customerSaleService.queryByPage(queryByPageDTO);
        return outDTO;
    }

    /**
     * 分页查询
     *
     * @param queryByPageDTO 筛选条件
     * @return 查询结果
     */
    @PostMapping("/queryPopList")
    @ApiOperation("查询列表弹窗(分页)")
    public QueryByPageOutDTO queryPopList(@RequestBody QueryByPageDTO queryByPageDTO) {
        QueryByPageOutDTO outDTO = this.customerSaleService.queryPopByPage(queryByPageDTO);
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
        QueryByIdOutDTO outDTO = this.customerSaleService.queryById(dto);
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
        AddOutDTO outDTO = customerSaleService.insert(dto);
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
        EditOutDTO outDTO = customerSaleService.update(dto);
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
        DeleteByIdOutDTO outDTO = customerSaleService.deleteById(dto);
        return outDTO;
    }

    /**
     * 导出销售客户列表
     *
     */
    @PostMapping("/export")
    @ApiOperation("导出销售客户列表")
    public void export(@RequestBody QueryByPageDTO dto) {
        List<CustomerSaleInfo> list = customerSaleService.queryListForExport(dto);
        ExcelUtil.exportExcel(list, "销售客户", CustomerSaleInfo.class);
    }

}

