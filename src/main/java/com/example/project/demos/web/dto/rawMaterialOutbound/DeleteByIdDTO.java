package com.example.project.demos.web.dto.rawMaterialOutbound;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DeleteByIdDTO {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 物料编号
     */
    @ApiModelProperty(value = "物料编号")
    private String materialCode;
    @ApiModelProperty(value = "物料名称")
    private String materialName;

    /**
     * 出库数量
     */
    @ApiModelProperty(value = "出库数量")
    private BigDecimal count;


    @ApiModelProperty(value = "出库方编号")
    private String outName;

    @ApiModelProperty(value = "领用人")
    private String receiverName;
}
