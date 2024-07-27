package com.example.project.demos.web.dto.salersOrder;

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
     * 单据号
     */
    @ApiModelProperty(value = "单据号")
    private String billCode;
    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    private String customerCode;
    @ApiModelProperty(value = "客户名称")
    private String customerName;
    /**
     * 联系人
     */
    @ApiModelProperty(value = "联系人")
    private String customerLink;
    /**
     * 客户联系方式
     */
    @ApiModelProperty(value = "客户联系方式")
    private String customerTel;
    /**
     * 装车日期
     */
    @ApiModelProperty(value = "装车日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date loadDate;
    /**
     * 装车数量
     */
    @ApiModelProperty(value = "装车数量")
    private BigDecimal loadNum;
    /**
     * 单价
     */
    @ApiModelProperty(value = "单价")
    private BigDecimal unitPrice;
    @ApiModelProperty(value = "总金额")
    private BigDecimal tollAmount;
    /**
     * 装车方式
     */
    @ApiModelProperty(value = "装车方式编码")
    private String loadType;
    @ApiModelProperty(value = "装车方式")
    private String loadTypeName;
    /**
     * 出库方编码
     */
    @ApiModelProperty(value = "出库方编码")
    private String outCode;
    @ApiModelProperty(value = "出库方名称")
    private String outName;

    @ApiModelProperty(value = "销售员英文名")
    private String saler;
    @ApiModelProperty(value = "销售员姓名")
    private String salerName;
    /**
     * 审批状态
     */
    @ApiModelProperty(value = "审核状态")
    private String approveState;
    @ApiModelProperty(value = "审核状态")
    private String approveStateName;
    /**
     * 审核人
     */
    @ApiModelProperty(value = "审核人")
    private String approveUser;
    @ApiModelProperty(value = "审核人姓名")
    private String approveUserName;
    /**
     * 审核意见
     */
    @ApiModelProperty(value = "审核意见")
    private String approveOpinion;
    /**
     * 审核时间
     */
    @ApiModelProperty(value = "审核时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date approveTime;
    /**
     * 确认状态
     */
    @ApiModelProperty(value = "确认状态编码")
    private String confirmState;
    @ApiModelProperty(value = "确认状态")
    private String confirmStateName;
    /**
     * 确认人
     */
    @ApiModelProperty(value = "确认人")
    private String confirmUser;
    @ApiModelProperty(value = "确认人")
    private String confirmUserName;
    /**
     * 确认意见
     */
    @ApiModelProperty(value = "确认意见")
    private String confirmOpinion;
    /**
     * 确认时间
     */
    @ApiModelProperty(value = "确认时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date confirmTime;
    /**
     * 单据状态
     */
    @ApiModelProperty(value = "单据状态")
    private String billState;
    @ApiModelProperty(value = "单据状态")
    private String billStateName;

    @ApiModelProperty(value = "冲销人")
    private String chargeoffUser;
    @ApiModelProperty(value = "冲销人姓名")
    private String chargeoffUserName;
    @ApiModelProperty(value = "冲销时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date chargeoffTime;
    @ApiModelProperty(value = "冲销意见")
    private String chargeoffOpinion;
    /**
     * 汇款
     */
    @ApiModelProperty(value = "汇款")
    private BigDecimal remit;
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
