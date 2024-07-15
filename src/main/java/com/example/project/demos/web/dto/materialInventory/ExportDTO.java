package com.example.project.demos.web.dto.materialInventory;

import com.alibaba.excel.annotation.ExcelProperty;
import com.example.project.demos.web.annotation.CellMerge;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * @author guanc
 * @version 创建时间：2024/7/12 14:13
 * @describe
 */
@Data
public class ExportDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 物料编号
     */
    @ExcelProperty(value = "物料编号")
    @CellMerge
    @ApiModelProperty(value = "物料编号")
    private String materialCode;

    @ExcelProperty(value = "物料名称")
    @CellMerge
    @ApiModelProperty(value = "物料名称")
    private String materialName;

    @ExcelProperty(value = "型号")
    @CellMerge
    @ApiModelProperty(value = "型号")
    private String modelName;

    @ExcelProperty(value = "单位名称")
    @CellMerge
    @ApiModelProperty(value = "单位名称")
    private String unitName;

    @ExcelProperty(value = "厂区/仓库编号")
    @ApiModelProperty(value = "厂区/仓库编号")
    private String stockCode;

    @ExcelProperty(value = "厂区/仓库名称")
    @ApiModelProperty(value = "厂区/仓库名称")
    private String stockName;

    @ExcelProperty(value = "库存")
    @ApiModelProperty(value = "库存")
    private BigDecimal inventoryNum;

    /**
     * 合计
     */
    @ExcelProperty(value = "合计")
    @CellMerge
    @ApiModelProperty(value = "合计")
    private BigDecimal tollNum;
}
