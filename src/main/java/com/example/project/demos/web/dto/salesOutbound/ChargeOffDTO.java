package com.example.project.demos.web.dto.salesOutbound;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ChargeOffDTO {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @ApiModelProperty(value = "物料编号")
    private String materialCode;
    @ApiModelProperty(value = "物料名称")
    private String materialName;

    /**
     * 单据号
     */
    @ApiModelProperty(value = "单据号")
    private String billCode;

    @ApiModelProperty(value = "客户名称")
    private String customerName;

    /**
     * 装车数量
     */
    @ApiModelProperty(value = "装车数量")
    private BigDecimal loadNum;


    @ApiModelProperty(value = "运输方式")
    private String transportTypeName;
    /**
     * 运费
     */
    @ApiModelProperty(value = "运费")
    private BigDecimal freight;
    @ApiModelProperty(value = "冲销意见")
    private String chargeOpinion;

}
