package com.example.project.demos.web.controller;

import com.example.project.demos.web.dto.list.SalersOrderInfo;
import com.example.project.demos.web.dto.salersOrder.*;
import com.example.project.demos.web.service.SalersOrderService;
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
 * 销售员下单维护表(salers_order)表控制层
 *
 * @author makejava
 * @since 2024-02-26 20:37:53
 */
@RestController
@RequestMapping("salersOrder")
@Api(tags="业务员下单维护表")
public class SalersOrderController {
    /**
     * 服务对象
     */
    @Resource
    private SalersOrderService salersOrderService;

    /**
     * 分页查询
     *
     * @param queryByPageDTO 筛选条件
     * @return 查询结果
     */
    @PostMapping("/queryPageList")
    @ApiOperation("查询列表(分页)")
    public QueryByPageOutDTO queryByPage(@RequestBody QueryByPageDTO queryByPageDTO) {
        QueryByPageOutDTO outDTO = this.salersOrderService.queryByPage(queryByPageDTO);
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
        QueryByIdOutDTO outDTO = this.salersOrderService.queryById(dto.getId());
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
        AddOutDTO outDTO = salersOrderService.insert(dto);
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
        EditOutDTO outDTO = salersOrderService.update(dto);
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
        DeleteByIdOutDTO outDTO = salersOrderService.deleteById(dto);
        return outDTO;
    }

    /**
     * 冲销提交
     *
     * @param dto 主键
     * @return 冲销操作
     */
    @PostMapping("/chargeOffSubmit")
    @ApiOperation("冲销提交")
    public ChargeOffOutDTO chargeOffSubmit(@RequestBody ChargeOffDTO dto) {
        ChargeOffOutDTO outDTO = salersOrderService.chargeOffSubmit(dto);
        return outDTO;
    }

    /**
     * 导出业务员下单列表
     *
     */
    @PostMapping("/export")
    @ApiOperation("导出业务员下单列表")
    public void export(@RequestBody QueryByPageDTO dto) {
        List<SalersOrderInfo> list = salersOrderService.queryListForExport(dto);
        ExcelUtil.exportExcel(list, "业务员下单", SalersOrderInfo.class);
    }

}

