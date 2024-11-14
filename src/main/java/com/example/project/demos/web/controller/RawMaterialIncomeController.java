package com.example.project.demos.web.controller;

import com.example.project.demos.web.dto.list.RawMaterialIncomeInfo;
import com.example.project.demos.web.dto.rawMaterialIncome.*;
import com.example.project.demos.web.service.RawMaterialIncomeService;
import com.example.project.demos.web.utils.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.net.UnknownHostException;
import java.util.List;

/**
 * 来料入库维护表(raw_material_income)表控制层
 *
 * @author makejava
 * @since 2024-02-26 20:37:53
 */
@RestController
@RequestMapping("rawMaterialIncome")
@Api(tags="来料入库维护表")
public class RawMaterialIncomeController {
    /**
     * 服务对象
     */
    @Resource
    private RawMaterialIncomeService rawMaterialIncomeService;

    /**
     * 分页查询
     *
     * @param dto 筛选条件
     * @return 查询结果
     */
    @PostMapping("/queryPageList")
    @ApiOperation("查询列表(分页)")
    public QueryByPageOutDTO queryByPage(@RequestBody QueryByPageDTO dto) {
        QueryByPageOutDTO outDTO = this.rawMaterialIncomeService.queryByPage(dto);
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
        QueryByIdOutDTO outDTO = this.rawMaterialIncomeService.queryById(dto.getId());
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
    public AddOutDTO add(@RequestBody AddDTO dto)  {
        AddOutDTO outDTO = rawMaterialIncomeService.insert(dto);
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
    public EditOutDTO edit(@RequestBody EditDTO dto) throws UnknownHostException {
        EditOutDTO outDTO = rawMaterialIncomeService.update(dto);
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
    public DeleteByIdOutDTO deleteById(@RequestBody DeleteByIdDTO dto) throws UnknownHostException {
        DeleteByIdOutDTO outDTO = rawMaterialIncomeService.deleteById(dto);
        return outDTO;
    }

    /**
     * 导出来料入库列表
     * HttpServletResponse response
     */
    @PostMapping("/export")
    @ApiOperation("导出来料入库列表")
    public void export(@RequestBody QueryByPageDTO dto) {
        List<RawMaterialIncomeInfo> list = rawMaterialIncomeService.queryListForExport(dto);
        ExcelUtil.exportExcel(list, "来料入库", RawMaterialIncomeInfo.class);
    }

}

