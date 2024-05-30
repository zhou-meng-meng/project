package com.example.project.demos.web.dto.list;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

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
