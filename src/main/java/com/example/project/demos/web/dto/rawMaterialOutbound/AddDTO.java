package com.example.project.demos.web.dto.rawMaterialOutbound;

import com.baomidou.mybatisplus.annotation.TableId;
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
    @ApiModelProperty(value = "物料编号")
    private String materialCode;

    /**
     * 型号
     */
    @ApiModelProperty(value = "型号")
    private String model;

    /**
     * 单位
     */
    @ApiModelProperty(value = "单位")
    private String unit;
    /**
     * 单价
     */
    @ApiModelProperty(value = "单价")
    private BigDecimal unitPrice;
    /**
     * 出库数量
     */
    @ApiModelProperty(value = "出库数量")
    private BigDecimal count;
    /**
     * 总金额
     */
    @ApiModelProperty(value = "总金额")
    private BigDecimal tollAmount;
    /**
     * 出库方编号
     */
    @ApiModelProperty(value = "出库方编号")
    private String outCode;

    /**
     * 领用人
     */
    @ApiModelProperty(value = "领用人")
    private String receiver;

    /**
     * 领用时间
     */
    @ApiModelProperty(value = "领用时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date receiveTime;
    /**
     * 单据号
     */
    @ApiModelProperty(value = "单据号")
    private String billNo;
    /**
     * 审批状态
     */
    @ApiModelProperty(value = "审批状态")
    private String approveState;
    /**
     * 单据状态
     */
    @ApiModelProperty(value = "单据状态")
    private String billState;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
}
