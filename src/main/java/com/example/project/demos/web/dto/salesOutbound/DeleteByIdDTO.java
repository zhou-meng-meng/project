package com.example.project.demos.web.dto.salesOutbound;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class DeleteByIdDTO {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @ApiModelProperty(value = "物料编号")
    private String materialCode;
    @ApiModelProperty(value = "物料名称")
    private String materialName;

    /**
     * 出库数量
     */
    @ApiModelProperty(value = "出库数量")
    private BigDecimal outCount;

    @ApiModelProperty(value = "购货客户名称")
    private String customerName;

    @ApiModelProperty(value = "出库方名称")
    private String outName;

    /**
     * 单据号
     */
    @ApiModelProperty(value = "单据号")
    private String billNo;


    @ApiModelProperty(value = "运输方式")
    private String transportTypeName;

    /**
     * 运费
     */
    @ApiModelProperty(value = "运费")
    private BigDecimal freight;

}
