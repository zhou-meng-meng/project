package com.example.project.demos.web.controller;

import com.example.project.demos.web.dto.list.MaterialInventoryInfo;
import com.example.project.demos.web.dto.materialInventory.*;
import com.example.project.demos.web.service.MaterialInventoryService;
import com.example.project.demos.web.utils.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.util.Comparator.nullsLast;

/**
 * 实时库存(material_inventory)表控制层
 *
 * @author makejava
 * @since 2024-02-26 20:37:53
 */
@RestController
@RequestMapping("materialInventory")
@Api(tags = "实时库存")
public class MaterialInventoryController {
    /**
     * 服务对象
     */
    @Resource
    private MaterialInventoryService MaterialInventoryService;

    /**
     * 分页查询
     *
     * @param queryByPageDTO 筛选条件
     * @return 查询结果
     */
    @PostMapping("/queryPageList")
    @ApiOperation("查询列表(分页)")
    public QueryByPageOutDTO queryByPage(@RequestBody QueryByPageDTO queryByPageDTO) {
        QueryByPageOutDTO outDTO = this.MaterialInventoryService.queryByPage(queryByPageDTO);
        return outDTO;
    }


    /**
     * 分页查询
     *
     * @param dto 筛选条件
     * @return 查询结果
     */
    @PostMapping("/queryPagePopList")
    @ApiOperation("弹窗查询列表(分页)")
    public QueryByPagePopOutDTO queryPagePopList(@RequestBody QueryByPagePopDTO dto) {
        QueryByPagePopOutDTO outDTO = this.MaterialInventoryService.queryPagePopList(dto);
        return outDTO;
    }

    /**
     * 库存维护
     *
     * @param dto 实体
     * @return 编辑结果
     */
    @PostMapping("/edit")
    @ApiOperation("库存维护")
    public EditOutDTO edit(@RequestBody EditDTO dto) {
       EditOutDTO outDTO = MaterialInventoryService.updateStockInventory(dto);
        return outDTO;
    }



    /**
     * 导出实时库存
     */
    @PostMapping("/export")
    @ApiOperation("导出实时库存")
    public void export(@RequestBody QueryByPageDTO dto) {
        List<MaterialInventoryInfo> list = this.MaterialInventoryService.queryByParam(dto);
        List<ExportDTO> list1 = new ArrayList<>();
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        for (MaterialInventoryInfo materialInventoryInfo : list) {
            ExportDTO exportDTO = new ExportDTO();
            BeanUtils.copyProperties(materialInventoryInfo, exportDTO);
            list1.add(exportDTO);
        }
        Collections.sort(list1, Comparator.comparing(ExportDTO::getMaterialCode, nullsLast(String::compareTo)).thenComparing(ExportDTO::getMaterialName, nullsLast(String::compareTo)).thenComparing(ExportDTO::getModelName, nullsLast(String::compareTo)).thenComparing(ExportDTO::getUnitName, nullsLast(String::compareTo)));
        ExcelUtil.exportExcel(list1, "实时库存", ExportDTO.class, true);
    }


}

