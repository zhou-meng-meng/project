package com.example.project.demos.web.dto.materialPackage;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.example.project.demos.web.annotation.CellMerge;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author guanc
 * @version 创建时间：2024/7/15 16:23
 * @describe
 */
@Data
public class MaterialPackageExportDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @CellMerge
    @ExcelProperty(value = "厂区名称")
    @ApiModelProperty(value = "厂区名称")
    private String factoryName;

    @CellMerge
    @ExcelProperty(value = "机器号")
    @ApiModelProperty(value = "机器号")
    private String machineName;

    /**
     * 日期
     */
    @CellMerge
    @ExcelProperty(value = "日期")
    @ApiModelProperty(value = "日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date packageDate;

    @CellMerge
    @ExcelProperty(value = "班次名称")
    @ApiModelProperty(value = "班次名称")
    private String dutyName;

    @ExcelProperty(value = "产料名称")
    @ApiModelProperty(value = "产料名称")
    private String materialName;

    /**
     * 每锅重量
     */
    @ExcelProperty(value = "每锅重量")
    @ApiModelProperty(value = "每锅重量")
    private BigDecimal potWeight;
    /**
     * 合计重量
     */
    @CellMerge
    @ExcelProperty(value = "合计重量")
    @ApiModelProperty(value = "合计重量")
    private BigDecimal tollWeight;
    /**
     * 应出袋数
     */
    @CellMerge
    @ExcelProperty(value = "应出袋数")
    @ApiModelProperty(value = "应出袋数")
    private BigDecimal shouldNum;
    /**
     * 实际袋数
     */
    @CellMerge
    @ExcelProperty(value = "实际袋数")
    @ApiModelProperty(value = "实际袋数")
    private BigDecimal actualNum;
    /**
     * 差额
     */
    @CellMerge
    @ExcelProperty(value = "差额")
    @ApiModelProperty(value = "差额")
    private BigDecimal balanceNum;


    @ExcelIgnore
    public List<Integer> mergeColumnIndex = Arrays.asList(0, 1, 2, 3, 6, 7, 8, 9);

}
