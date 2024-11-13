package com.example.project.demos.web.dto.list;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.example.project.demos.web.utils.LocalDateConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 导出备份用
 */
@Data
public class CustomerPayDetailInfoForExport {

    /**
     * 来料/销售日期
     */
    @ApiModelProperty(value = "日期")
    private String materialDate;

    @ApiModelProperty(value = "厂区")
    private String factoryName;
    @ApiModelProperty(value = "单据号")
    private String billNo;

    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    private String customerCode;

    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ApiModelProperty(value = "物料编号")
    private String materialCode;

    @ApiModelProperty(value = "物料名称")
    private String materialName;

    @ApiModelProperty(value = "型号")
    private String modelName;
    /**
     * 单位
     */
    @ApiModelProperty(value = "单位")
    private String unitName;

    @ApiModelProperty(value = "单价")
    private BigDecimal unitPrice;

    @ApiModelProperty(value = "数量")
    private BigDecimal materialCount;

    @ApiModelProperty(value = "物料金额")
    private BigDecimal materialBalance;


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
     * 打款金额
     */
    @ApiModelProperty(value = "打款金额")
    private BigDecimal payBalance;

    /**
     * 账面余额
     */
    @ApiModelProperty(value = "账面余额")
    private BigDecimal bookBalance;

    @ApiModelProperty(value = "运费")
    private BigDecimal freight;

    @ApiModelProperty(value = "经办人名字")
    private String operatorByName;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建者名字")
    private String createByName;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "更新者名字")
    private String updateByName;

    @ApiModelProperty(value = "更新时间")
    private String updateTime;

}
