package com.example.project.demos.web.dto.rawMaterialIncome;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class EditDTO {
    /**
     * 自增主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;
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
     * 供货商编号
     */
    @ApiModelProperty(value = "供货商编号")
    private String supplyerCode;
    /**
     * 厂区编号
     */
    @ApiModelProperty(value = "厂区编号")
    private String factoryCode;
    @ApiModelProperty(value = "厂区名称")
    private String factoryName;
    /**
     * 单据号
     */
    @ApiModelProperty(value = "单据号")
    private String billNo;
    /**
     * 进货人
     */
    @ApiModelProperty(value = "进货人")
    private String materialBuyer;
    /**
     * 进货时间
     */
    @ApiModelProperty(value = "进货时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date materialBuytime;
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
     * 总数量
     */
    @ApiModelProperty(value = "总数量")
    private BigDecimal count;
    /**
     * 总金额
     */
    @ApiModelProperty(value = "总金额")
    private BigDecimal tollAmout;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
}
