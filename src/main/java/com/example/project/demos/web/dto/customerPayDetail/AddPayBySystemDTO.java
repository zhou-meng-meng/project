package com.example.project.demos.web.dto.customerPayDetail;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class AddPayBySystemDTO {

    @ApiModelProperty(value = "id")
    private Long id;
    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    private String customerCode;
    @ApiModelProperty(value = "物料编号")
    private String materialCode;
    @ApiModelProperty(value = "单价")
    private BigDecimal unitPrice;
    @ApiModelProperty(value = "数量")
    private BigDecimal materialCount;

    @ApiModelProperty(value = "来料/销售日期")
    private Date materialDate;

    /**
     * 物料总金额
     */
    @ApiModelProperty(value = "物料总金额")
    private BigDecimal materialBalance;

    /**
     * 账面余额
     */
    @ApiModelProperty(value = "账面余额")
    private BigDecimal bookBalance;
    /**
     * 打款金额
     */
    @ApiModelProperty(value = "打款金额")
    private BigDecimal payBalance;
    /**
     * 退回金额
     */
    @ApiModelProperty(value = "退回金额")
    private BigDecimal returnBalance;

    @ApiModelProperty(value = "折扣金额")
    private BigDecimal discountBalance;

    /**
     * 付款类型  0-入款 1-出款
     */
    @ApiModelProperty(value = "付款类型  0-入款 1-出款")
    private String payType;
    @ApiModelProperty(value = "打款日期")
    private Date payDate;
    @ApiModelProperty(value = "是否默认值 Y-是；N-否  前端固定值N")
    private String isDefault;
    @ApiModelProperty(value = "经办人英文名")
    private String operatorBy;

    /**
     *
     */
    @ApiModelProperty(value = "创建人")
    private String createBy;
    /**
     *
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;


    public AddPayBySystemDTO(Long id,String customerCode,String materialCode,BigDecimal unitPrice,BigDecimal materialCount, Date materialDate,BigDecimal materialBalance,BigDecimal bookBalance,BigDecimal payBalance,BigDecimal returnBalance,BigDecimal discountBalance,String payType,Date payDate,String isDefault,String operatorBy,Date date,String remark){
        this.id = id;
        this.customerCode = customerCode;
        this.materialCode = materialCode;
        this.unitPrice = unitPrice;
        this.materialCount = materialCount;
        this.materialDate = materialDate;
        this.materialBalance= materialBalance;
        this.bookBalance= bookBalance;
        this.payBalance= payBalance;
        this.returnBalance = returnBalance;
        this.discountBalance = discountBalance;
        this.payType =payType;
        this.payDate = payDate;
        this.isDefault = isDefault;
        this.createBy = operatorBy;
        this.remark = remark;
    }

}
