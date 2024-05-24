package com.example.project.demos.web.dto.salesOutbound;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class EditDTO {
    /**
     * 自增主键
     */
    @ApiModelProperty(value = "自增主键")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 物料编号
     */
    @ApiModelProperty(value = "物料编号")
    private String materialCode;

    /**
     * 出库数量
     */
    @ApiModelProperty(value = "出库数量")
    private BigDecimal outCount;
    /**
     * 单价
     */
    @ApiModelProperty(value = "单价")
    private BigDecimal unitPrice;
    /**
     * 总金额
     */
    @ApiModelProperty(value = "总金额")
    private BigDecimal tollAmount;
    @ApiModelProperty(value = "购货客户编号")
    private String customerCode;

    /**
     * 出库方编号
     */
    @ApiModelProperty(value = "出库方编号")
    private String outCode;

    /**
     * 销售员英文名
     */
    @ApiModelProperty(value = "销售员英文名")
    private String saler;

    /**
     * 销售时间
     */
    @ApiModelProperty(value = "销售员名称")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date saleTime;
    /**
     * 审批状态
     */
    @ApiModelProperty(value = "审批状态")
    private String approveState;

    /**
     * 审批意见
     */
    @ApiModelProperty(value = "审批意见")
    private String approveOpinion;
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
     * 运输方式
     */
    @ApiModelProperty(value = "运输方式")
    private String transportType;

    /**
     * 运费
     */
    @ApiModelProperty(value = "运费")
    private BigDecimal freight;
    /**
     * 回款金额
     */
    @ApiModelProperty(value = "回款金额")
    private BigDecimal receivePayment;
    /**
     * 回款时间
     */
    @ApiModelProperty(value = "回款时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date receivePaymentTime;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
}
