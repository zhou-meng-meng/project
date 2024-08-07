package com.example.project.demos.web.dto.salersOrder;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class AddDTO {

    @ApiModelProperty(value = "物料编号")
    private String materialCode;
    @ApiModelProperty(value = "物料名称")
    private String materialName;

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

    @ApiModelProperty(value = "销售员英文名")
    private String saler;
    @ApiModelProperty(value = "销售员姓名")
    private String salerName;
    /**
     * 汇款
     */
    @ApiModelProperty(value = "汇款")
    private BigDecimal remit;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
}
