package com.example.project.demos.web.dto.materialInventory;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class AddDTO {

    /**
     * 物料编号
     */
    @ApiModelProperty(value = "物料编号")
    private String materialCode;

    @ApiModelProperty(value = "厂区/仓库编号")
    private String stockCode;
    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    private BigDecimal inventoryNum;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
}
