package com.example.project.demos.web.controller;

import com.example.project.demos.web.dto.list.RawMaterialIncomeInfo;
import com.example.project.demos.web.dto.list.RawMaterialOutboundInfo;
import com.example.project.demos.web.dto.rawMaterialOutbound.*;
import com.example.project.demos.web.service.RawMaterialOutboundService;
import com.example.project.demos.web.utils.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 原材料出库维护表(rebuild_outbound)表控制层
 *
 * @author makejava
 * @since 2024-02-26 20:37:53
 */
@RestController
@RequestMapping("rawMaterialOutbound")
@Api(tags="原材料出库维护表")
public class RawMaterialOutboundController {
    /**
     * 服务对象
     */
    @Resource
    private RawMaterialOutboundService rawMaterialOutboundService;

    /**
     * 分页查询
     *
     * @param queryByPageDTO 筛选条件
     * @return 查询结果
     */
    @PostMapping("/queryPageList")
    @ApiOperation("查询列表(分页)")
    public QueryByPageOutDTO queryByPage(@RequestBody QueryByPageDTO queryByPageDTO) {
        QueryByPageOutDTO outDTO = this.rawMaterialOutboundService.queryByPage(queryByPageDTO);
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
        QueryByIdOutDTO outDTO = this.rawMaterialOutboundService.queryById(dto.getId());
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
        AddOutDTO outDTO = rawMaterialOutboundService.insert(dto);
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
        EditOutDTO outDTO = rawMaterialOutboundService.update(dto);
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
        DeleteByIdOutDTO outDTO = rawMaterialOutboundService.deleteById(dto);
        return outDTO;
    }

    /**
     * 导出原材料出库列表
     */
    @PostMapping("/export")
    @ApiOperation("导出原材料出库列表")
    public void export(@RequestBody QueryByPageDTO dto, HttpServletResponse response) {
        List<RawMaterialOutboundInfo> list = rawMaterialOutboundService.queryListForExport(dto);
        ExcelUtil.exportExcel(list, "原材料出库", RawMaterialOutboundInfo.class,response);
    }

}

