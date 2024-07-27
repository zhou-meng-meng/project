package com.example.project.demos.web.dto.list;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class MaterialDosageTollAmountInfo {

    /**
     * 磨粉棒重量
     */
    @ApiModelProperty(value = "磨粉棒重量")
    private BigDecimal grindingWeightToll;
    /**
     * 机器磅重量
     */
    @ApiModelProperty(value = "机器磅重量")
    private BigDecimal machineWeightToll;
    /**
     * 差额（磨粉棒重量-机器磅重量）
     */
    @ApiModelProperty(value = "差额（磨粉棒重量-机器磅重量）")
    private BigDecimal differentWeightToll;

}
