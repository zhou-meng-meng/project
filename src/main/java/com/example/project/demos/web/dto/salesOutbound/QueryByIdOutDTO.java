package com.example.project.demos.web.dto.salesOutbound;

import com.baomidou.mybatisplus.annotation.TableId;
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
    @ApiModelProperty(value = "自增主键")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 物料编号
     */
    @ApiModelProperty(value = "物料编号")
    private String materialCode;
    @ApiModelProperty(value = "物料名称")
    private String materialName;

    @ApiModelProperty(value = "型号")
    private String model;
    @ApiModelProperty(value = "型号")
    private String modelName;

    @ApiModelProperty(value = "单位")
    private String unit;
    @ApiModelProperty(value = "单位名称")
    private String unitName;

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
    @ApiModelProperty(value = "购货客户名称")
    private String customerName;
    /**
     * 出库方编号
     */
    @ApiModelProperty(value = "出库方编号")
    private String outCode;
    @ApiModelProperty(value = "出库方名称")
    private String outName;
    /**
     * 销售员英文名
     */
    @ApiModelProperty(value = "销售员英文名")
    private String saler;
    @ApiModelProperty(value = "销售员名称")
    private String salerName;
    /**
     * 销售时间
     */
    @ApiModelProperty(value = "销售时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date saleTime;
    /**
     * 审批状态
     */
    @ApiModelProperty(value = "审批状态")
    private String approveState;
    @ApiModelProperty(value = "审批状态")
    private String approveStateName;
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
    @ApiModelProperty(value = "单据状态")
    private String billStateName;
    /**
     * 运输方式
     */
    @ApiModelProperty(value = "运输方式")
    private String transportType;
    @ApiModelProperty(value = "运输方式")
    private String transportTypeName;
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
