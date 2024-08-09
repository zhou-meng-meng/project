package com.example.project.demos.web.dto.materialInventory;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.example.project.demos.web.annotation.CellMerge;
import com.example.project.demos.web.dto.list.MaterialInventoryStockInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class EditDTO {
    /**
     * 物料编号
     */
    @ApiModelProperty(value = "物料编号")
    private String materialCode;

    @ApiModelProperty(value = "物料名称")
    private String materialName;

    @ApiModelProperty(value = "厂区/仓库编号")
    private String stockCode;
    @ApiModelProperty(value = "厂区/仓库名称")
    private String stockName;
    @ApiModelProperty(value = "库存")
    private BigDecimal inventoryNum;

    @ApiModelProperty(value = "备注")
    private String remark;

}
