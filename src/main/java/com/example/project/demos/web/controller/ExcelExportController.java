package com.example.project.demos.web.controller;


import cn.hutool.core.bean.BeanUtil;
import com.example.project.demos.web.dto.guaFinancialDebt.ExcelExportDto;
import com.example.project.demos.web.service.ExcelExportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 导出Excel
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-08 15:30:25
 */
@RestController
@RequestMapping("excelExport")
@Api(tags="导出Excel")
public class ExcelExportController {

    @Autowired
    private ExcelExportService excelExportService;

    @GetMapping("/export")
    @ApiOperation("导出报表")
    public void report(ExcelExportDto dto, HttpServletResponse response) {
        excelExportService.exportExcel(dto.getType(), BeanUtil.beanToMap(dto),response,"");
    }


}
