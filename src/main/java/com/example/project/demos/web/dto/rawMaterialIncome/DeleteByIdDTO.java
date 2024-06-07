package com.example.project.demos.web.dto.rawMaterialIncome;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DeleteByIdDTO {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "物料编号")
    private String materialCode;
    @ApiModelProperty(value = "物料名称")
    private String materialName;

    @ApiModelProperty(value = "总数量")
    private BigDecimal count;

    @ApiModelProperty(value = "进货人")
    private String materialBuyerName;

    @ApiModelProperty(value = "供货商名称")
    private String supplyerName;
    @ApiModelProperty(value = "入库方名称")
    private String inName;
}
