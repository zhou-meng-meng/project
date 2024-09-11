package com.example.project.demos.web.dto.salesReturn;

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
    @TableId
    @ApiModelProperty(value = "自增主键")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "客户编号")
    private String customerCode;
    @ApiModelProperty(value = "客户名称")
    private String customerName;

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

    @ApiModelProperty(value = "运费")
    private BigDecimal freight;

    @ApiModelProperty(value = "审核人")
    private String approveUser;
    @ApiModelProperty(value = "审核人姓名")
    private String approveUserName;
    @ApiModelProperty(value = "审核状态")
    private String approveState;
    @ApiModelProperty(value = "审核状态")
    private String approveStateName;
    /**
     * 审批意见
     */
    @ApiModelProperty(value = "审核意见")
    private String approveOpinion;

    @ApiModelProperty(value = "审核时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date approveTime;

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
     * 退回方编号
     */
    @ApiModelProperty(value = "退回方编号")
    private String inCode;
    @ApiModelProperty(value = "退回方名称")
    private String inName;
    /**
     * 退回人
     */
    @ApiModelProperty(value = "退回人")
    private String returnUser;
    @ApiModelProperty(value = "退回人")
    private String returnUserName;
    /**
     * 退货时间
     */
    @ApiModelProperty(value = "退货时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date returnTime;
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
     * 订货地址
     */
    @ApiModelProperty(value = "订货地址")
    private String orderAddress;

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
