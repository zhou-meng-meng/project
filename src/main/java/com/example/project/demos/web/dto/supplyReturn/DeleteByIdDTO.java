package com.example.project.demos.web.dto.supplyReturn;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DeleteByIdDTO {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "客户名称")
    private String customerName;
    /**
     * 物料编号
     */
    @ApiModelProperty(value = "物料编号")
    private String materialCode;
    @ApiModelProperty(value = "物料名称")
    private String materialName;

    /**
     * 退回数量
     */
    @ApiModelProperty(value = "退回数量")
    private BigDecimal returnCount;


    @ApiModelProperty(value = "退回方名称")
    private String outName;

    @ApiModelProperty(value = "退回人姓名")
    private String returnUserName;
}
