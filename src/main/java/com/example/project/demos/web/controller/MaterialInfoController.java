package com.example.project.demos.web.controller;


import com.example.project.demos.web.dto.list.MaterialInfo;
import com.example.project.demos.web.dto.list.SupplyReturnInfo;
import com.example.project.demos.web.dto.materialInfo.*;
import com.example.project.demos.web.service.MaterialInfoService;
import com.example.project.demos.web.utils.ExcelUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 物料信息维护表
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-08 15:30:25
 */
@RestController
@RequestMapping("materialinfo")
public class MaterialInfoController {
    @Autowired
    private MaterialInfoService materialInfoService;

    /**
     * 分页查询
     *
     * @param queryByPageDTO 筛选条件
     * @return 查询结果
     */
    @PostMapping("/queryMaterialInfoList")
    @ApiOperation("查询列表(分页)")
    public QueryByPageOutDTO queryByPage(@RequestBody QueryByPageDTO queryByPageDTO) {
        QueryByPageOutDTO outDTO = this.materialInfoService.queryByPage(queryByPageDTO);
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
        QueryByIdOutDTO outDTO = this.materialInfoService.queryById(dto.getId());
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
        AddOutDTO outDTO = materialInfoService.insert(dto);
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
        EditOutDTO outDTO = materialInfoService.update(dto);
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
        DeleteByIdOutDTO outDTO = materialInfoService.deleteById(dto);
        return outDTO;
    }

    /**
     * 导出物料维护列表
     */
    @PostMapping("/export")
    @ApiOperation("导出物料维护列表")
    public void export(@RequestBody QueryByPageDTO dto, HttpServletResponse response) {
        List<MaterialInfo> list = materialInfoService.queryListForExport(dto);
        ExcelUtil.exportExcel(list, "物料维护", MaterialInfo.class,response);
    }

}
