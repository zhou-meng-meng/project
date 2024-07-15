package com.example.project.demos.web.dto.list;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.example.project.demos.web.annotation.CellMerge;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-23 14:35:27
 */
@Data

public class MaterialInventoryInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @ExcelIgnore
    @ApiModelProperty(value = "自增主键")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 物料编号
     */
    @CellMerge
    @ApiModelProperty(value = "物料编号")
    private String materialCode;
    @CellMerge
    @ApiModelProperty(value = "物料名称")
    private String materialName;

    @ExcelIgnore
    @ApiModelProperty(value = "型号")
    private String model;
    @CellMerge
    @ApiModelProperty(value = "型号")
    private String modelName;

    @ExcelIgnore
    @ApiModelProperty(value = "单位")
    private String unit;
    @CellMerge
    @ApiModelProperty(value = "单位名称")
    private String unitName;

    @ExcelIgnore
    @ApiModelProperty(value = "各厂区/仓库库存集合")
    private List<MaterialInventoryStockInfo> stockInfo;

    @ApiModelProperty(value = "厂区/仓库编号")
    private String stockCode;
    @ApiModelProperty(value = "厂区/仓库名称")
    private String stockName;
    @ApiModelProperty(value = "库存")
    private BigDecimal inventoryNum;

    /**
     * 合计
     */
    @CellMerge
    @ApiModelProperty(value = "合计")
    private BigDecimal tollNum;

    @ExcelIgnore
    @ApiModelProperty(value = "最后更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateTime;

    /**
     * 备注
     */
    @ExcelIgnore
    @ApiModelProperty(value = "备注")
    private String remark;

    @ExcelIgnore
    @ApiModelProperty(value = "分组标识")
    private String groupBy;

}
