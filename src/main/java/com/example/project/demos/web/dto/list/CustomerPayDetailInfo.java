package com.example.project.demos.web.dto.list;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class CustomerPayDetailInfo {
    /**
     * 自增主键
     */
    @ExcelIgnore
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "自增主键")
    private Long id;
    /**
     * 客户编号
     */
    @ExcelProperty(value = "客户编号")
    @ApiModelProperty(value = "客户编号")
    private String customerCode;

    @ExcelProperty(value = "客户名称")
    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ExcelProperty(value = "单价")
    @ApiModelProperty(value = "单价")
    private BigDecimal unitPrice;

    @ExcelProperty(value = "数量")
    @ApiModelProperty(value = "数量")
    private BigDecimal materialCount;

    /**
     * 来料/销售日期
     */
    @ExcelProperty(value = "来料/销售日期")
    @ApiModelProperty(value = "来料/销售日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date materialDate;

    /**
     * 账面余额
     */
    @ExcelProperty(value = "账面余额")
    @ApiModelProperty(value = "账面余额")
    private BigDecimal bookBalance;

    @ExcelProperty(value = "物料金额")
    @ApiModelProperty(value = "物料金额")
    private BigDecimal materialBalance;


    /**
     * 打款金额
     */
    @ExcelProperty(value = "打款金额")
    @ApiModelProperty(value = "打款金额")
    private BigDecimal payBalance;
    /**
     * 退回金额
     */
    @ExcelProperty(value = "退回金额")
    @ApiModelProperty(value = "退回金额")
    private BigDecimal returnBalance;

    @ExcelProperty(value = "折扣金额")
    @ApiModelProperty(value = "折扣金额")
    private BigDecimal discountBalance;

    @ExcelIgnore
    @ApiModelProperty(value = "经办人英文名")
    private String operatorBy;

    @ExcelProperty(value = "经办人")
    @ApiModelProperty(value = "经办人名字")
    private String operatorByName;

    /**
     * 付款类型  0-入款 1-出款
     */
    @ExcelIgnore
    @ApiModelProperty(value = "付款类型  0-入款 1-出款")
    private String payType;
    @ExcelIgnore
    @ApiModelProperty(value = "付款类型")
    private String payTypeName;
    @ExcelIgnore
    @ApiModelProperty(value = "是否默认值 Y-是；N-否  前端固定值N")
    private String isDefault;
    /**
     * 创建者
     */
    @ExcelIgnore
    @ApiModelProperty(value = "创建者英文名")
    private String createBy;
    @ExcelProperty(value = "创建者名字")
    @ApiModelProperty(value = "创建者名字")
    private String createByName;
    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    /**
     * 更新者
     */
    @ExcelIgnore
    @ApiModelProperty(value = "更新者英文名")
    private String updateBy;
    @ExcelProperty(value = "更新者名字")
    @ApiModelProperty(value = "更新者名字")
    private String updateByName;
    /**
     * 更新时间
     */
    @ExcelProperty(value = "更新时间")
    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    @ApiModelProperty(value = "备注")
    private String remark;
}
