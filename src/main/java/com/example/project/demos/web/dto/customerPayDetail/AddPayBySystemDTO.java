package com.example.project.demos.web.dto.customerPayDetail;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class AddPayBySystemDTO {

    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "id")
    private Long businessId;
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

    @ApiModelProperty(value = "运费")
    private BigDecimal freight;

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

    @ApiModelProperty(value = "厂区编号")
    private String factoryCode;

    @ApiModelProperty(value = "单据号")
    private String billNo;

    /**
     * 退回数量
     */
    @ApiModelProperty(value = "退回数量")
    private BigDecimal returnCount;

    /**
     * 退回单价
     */
    @ApiModelProperty(value = "退回单价")
    private BigDecimal returnUnitPrice;
    /**
     * 退回金额
     */
    @ApiModelProperty(value = "退回金额")
    private BigDecimal returnBalance;

    /**
     * 税金
     */
    @ApiModelProperty(value = "税金")
    private BigDecimal taxBalance;

    /**
     * 其他金额
     */
    @ApiModelProperty(value = "其他金额")
    private BigDecimal otherBalance;

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


    /**
     * 正常来料/销售使用
     * @param id
     * @param businessId
     * @param factoryCode
     * @param billNo
     * @param customerCode
     * @param materialCode
     * @param unitPrice
     * @param materialCount
     * @param materialDate
     * @param materialBalance
     * @param isDefault
     * @param operatorBy
     * @param date
     * @param remark
     */
    public AddPayBySystemDTO(Long id,Long businessId,String factoryCode,String billNo,String customerCode,String materialCode,Date materialDate,BigDecimal unitPrice,BigDecimal materialCount, BigDecimal materialBalance,String isDefault,String operatorBy,Date date,String remark){
        this.id = id;
        this.businessId = businessId;
        this.factoryCode = factoryCode;
        this.billNo = billNo;
        this.customerCode = customerCode;
        this.materialCode = materialCode;
        this.unitPrice = unitPrice;
        this.materialCount = materialCount;
        this.materialDate = materialDate;
        this.materialBalance= materialBalance;
        this.isDefault = isDefault;
        this.createBy = operatorBy;
        this.operatorBy = operatorBy;
        this.remark = remark;
    }

    /**
     * 退货使用
     * @param id
     * @param businessId
     * @param factoryCode
     * @param billNo
     * @param customerCode
     * @param materialCode
     * @param materialDate
     * @param isDefault
     * @param operatorBy
     * @param date
     * @param remark
     * @param returnUnitPrice
     * @param returnCount
     * @param returnBalance
     */
    public AddPayBySystemDTO(Long id,Long businessId,String factoryCode,String billNo,String customerCode,String materialCode,Date materialDate,String isDefault,String operatorBy,Date date,String remark,BigDecimal returnUnitPrice,BigDecimal returnCount, BigDecimal returnBalance){
        this.id = id;
        this.businessId = businessId;
        this.factoryCode = factoryCode;
        this.billNo = billNo;
        this.customerCode = customerCode;
        this.materialCode = materialCode;
        this.returnUnitPrice = returnUnitPrice;
        this.returnCount = returnCount;
        this.materialDate = materialDate;
        this.returnBalance= returnBalance;
        this.isDefault = isDefault;
        this.createBy = operatorBy;
        this.operatorBy = operatorBy;
        this.remark = remark;
    }

}
