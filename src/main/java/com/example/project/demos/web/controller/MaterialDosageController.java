package com.example.project.demos.web.controller;

import com.example.project.demos.web.dto.list.MaterialDosageInfo;
import com.example.project.demos.web.dto.list.MaterialInfo;
import com.example.project.demos.web.dto.materialDosage.*;
import com.example.project.demos.web.service.MaterialDosageService;
import com.example.project.demos.web.utils.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 物料用量表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-08 17:08:07
 */
@RestController
@RequestMapping("materialdosage")
@Api(tags="物料用量表")
public class MaterialDosageController {
    /**
     * 服务对象
     */
    @Resource
    private MaterialDosageService materialDosageService;

    /**
     * 分页查询
     *
     * @param queryByPageDTO 筛选条件
     * @return 查询结果
     */
    @PostMapping("/queryMaterialDosageList")
    @ApiOperation("查询列表(分页)")
    public QueryByPageOutDTO queryByPage(@RequestBody QueryByPageDTO queryByPageDTO) {
        QueryByPageOutDTO outDTO = this.materialDosageService.queryByPage(queryByPageDTO);
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
        QueryByIdOutDTO outDTO = this.materialDosageService.queryById(dto.getId());
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
        AddOutDTO outDTO = materialDosageService.insert(dto);
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
        EditOutDTO outDTO = materialDosageService.update(dto);
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
        DeleteByIdOutDTO outDTO = materialDosageService.deleteById(dto);
        return outDTO;
    }

    /**
     * 导出用量表列表
     */
    @PostMapping("/export")
    @ApiOperation("导出用量表列表")
    public void export(@RequestBody QueryByPageDTO dto) {
        List<MaterialDosageInfo> list = materialDosageService.queryListForExport(dto);
        ExcelUtil.exportExcel(list, "用量表", MaterialDosageInfo.class);
    }

}
