package com.example.project.demos.web.dto.approveOperationQueue;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DealApproveQueueDTO {

    @ApiModelProperty(value = "主键id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "单价")
    private BigDecimal unitPrice;

    @ApiModelProperty(value = "总金额")
    private BigDecimal tollAmount;

    @ApiModelProperty(value = "运费")
    private BigDecimal freight;

    @ApiModelProperty(value = "入库方编号")
    private String inCode;
    @ApiModelProperty(value = "入库方名称")
    private String inName;
    @ApiModelProperty(value = "出库方编号")
    private String outCode;
    @ApiModelProperty(value = "出库方名称")
    private String outName;

    @ApiModelProperty(value = "审核结果 1-同意；2-拒绝")
    private String result;

    @ApiModelProperty(value = "审核意见")
    private String opinion;

    @ApiModelProperty(value = "单据号")
    private String billNo;

    @ApiModelProperty(value = "客户名称")
    private String customerName;

}
