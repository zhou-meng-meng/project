package com.example.project.demos.web.dto.customerPayDetail;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EditBookBalanceDTO {

    /**
     * 维护初始账面余额
     */
    @ApiModelProperty(value = "初始账面余额")
    private BigDecimal bookBalance;

    @ApiModelProperty(value = "客户编号")
    private String customerCode;

    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ApiModelProperty(value = "备注")
    private String remark;

}
