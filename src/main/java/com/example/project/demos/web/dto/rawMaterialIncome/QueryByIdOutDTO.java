package com.example.project.demos.web.dto.rawMaterialIncome;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class QueryByIdOutDTO {

    private static final long serialVersionUID = -89026850526790880L;
    /**
     * 自增主键
     */
    @ApiModelProperty(value = "主键")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 物料编号
     */
    @ApiModelProperty(value = "物料编号")
    private String materialCode;
    @ApiModelProperty(value = "物料名称")
    private String materialName;
    /**
     * 型号
     */
    @ApiModelProperty(value = "型号")
    private String model;
    @ApiModelProperty(value = "型号")
    private String modelName;
    /**
     * 单位
     */
    @ApiModelProperty(value = "单位")
    private String unit;
    @ApiModelProperty(value = "单位")
    private String unitName;

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
    private String inCodeName;
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
    @ApiModelProperty(value = "进货人")
    private String materialBuyerName;
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
    @ApiModelProperty(value = "审批状态")
    private String approveStateName;
    @ApiModelProperty(value = "审批意见")
    private String approveOpinion;

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
     * 创建者
     */
    @ApiModelProperty(value = "创建者英文名")
    private String createBy;
    @ApiModelProperty(value = "创建者名字")
    private String createByName;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 更新者
     */
    @ApiModelProperty(value = "更新者英文名")
    private String updateBy;
    @ApiModelProperty(value = "更新者名字")
    private String updateByName;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 操作结果编码:null
     */
    @ApiModelProperty(value = "操作结果编码")
    private String errorCode;

    /**
     * 操作结果信息:null
     */
    @ApiModelProperty(value = "操作结果信息")
    private String errorMsg;
}
