package com.example.project.demos.web.dto.salersOrderReturn;

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
    @ApiModelProperty(value = "退回数量")
    private BigDecimal returnCount;

    @ApiModelProperty(value = "购货客户名称")
    private String customerName;
    @ApiModelProperty(value = "单价")
    private BigDecimal unitPrice;
    /**
     * 总金额
     */
    @ApiModelProperty(value = "总金额")
    private BigDecimal tollAmount;
    /**
     * 单据号
     */
    @ApiModelProperty(value = "单据号")
    private String billNo;
    @ApiModelProperty(value = "退回人")
    private String returnUserName;
    @ApiModelProperty(value = "退货日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date returnTime;


}
