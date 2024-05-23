package com.example.project.demos.web.dto.salesReturn;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AddDTO {


    /**
     * 物料编号
     */
    @ApiModelProperty(value = "客户编号")
    private String customerCode;
    /**
     * 物料编号
     */
    @ApiModelProperty(value = "物料编号")
    private String materialCode;

    /**
     * 单价
     */
    @ApiModelProperty(value = "单价")
    private BigDecimal unitPrice;
    /**
     * 退回数量
     */
    @ApiModelProperty(value = "退回数量")
    private BigDecimal returnCount;
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
    /**
     * 单据状态
     */
    @ApiModelProperty(value = "单据状态")
    private String billState;

    /**
     * 退回方编号
     */
    @ApiModelProperty(value = "退回方编号")
    private String inCode;
    /**
     * 退回人
     */
    @ApiModelProperty(value = "退回人")
    private String returnUser;

    /**
     * 退货时间
     */
    @ApiModelProperty(value = "退货时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date returnTime;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
}
