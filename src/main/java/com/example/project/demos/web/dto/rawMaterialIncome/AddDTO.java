package com.example.project.demos.web.dto.rawMaterialIncome;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AddDTO {

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
    @ApiModelProperty(value = "物料名称")
    private String materialName;

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
    @ApiModelProperty(value = "供货商名称")
    private String supplyerName;

    /**
     * 入库方编号
     */
    @ApiModelProperty(value = "入库方编号")
    private String inCode;
    @ApiModelProperty(value = "入库方名称")
    private String inName;

    /**
     * 单据号
     */
    @ApiModelProperty(value = "单据号")
    private String billNo;
    /**
     * 进货人
     */
    @ApiModelProperty(value = "进货人编号")
    private String materialBuyer;
    @ApiModelProperty(value = "进货人")
    private String materialBuyerName;

    /**
     * 进货时间
     */
    @ApiModelProperty(value = "进货时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
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
