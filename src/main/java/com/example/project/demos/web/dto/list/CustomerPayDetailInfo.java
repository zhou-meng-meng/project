package com.example.project.demos.web.dto.list;

import com.alibaba.excel.annotation.ExcelIgnore;
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

@Data
@ExcelIgnoreUnannotated
public class CustomerPayDetailInfo {
    /**
     * 自增主键
     */
    
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "自增主键")
    private Long id;

    
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "各业务主键")
    private Long businessId;

    /**
     * 来料/销售日期
     */
    @ExcelProperty(value = "日期",converter = LocalDateConverter.class)
    @ApiModelProperty(value = "日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date materialDate;

    @ExcelProperty(value = "厂区")
    @ApiModelProperty(value = "厂区")
    private String factoryName;
    @ExcelProperty(value = "单据号")
    @ApiModelProperty(value = "单据号")
    private String billNo;

    /**
     * 客户编号
     */
    @ExcelProperty(value = "客户编号")
    @ApiModelProperty(value = "客户编号")
    private String customerCode;

    @ExcelProperty(value = "客户名称")
    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ExcelProperty(value = "物料编号")
    @ApiModelProperty(value = "物料编号")
    private String materialCode;

    @ExcelProperty(value = "物料名称")
    @ApiModelProperty(value = "物料名称")
    private String materialName;

    @ExcelProperty(value = "型号")
    @ApiModelProperty(value = "型号")
    private String modelName;
    /**
     * 单位
     */
    @ExcelProperty(value = "单位")
    @ApiModelProperty(value = "单位")
    private String unitName;

    @ExcelProperty(value = "单价")
    @ApiModelProperty(value = "单价")
    private BigDecimal unitPrice;

    @ExcelProperty(value = "数量")
    @ApiModelProperty(value = "数量")
    private BigDecimal materialCount;

    @ExcelProperty(value = "物料金额")
    @ApiModelProperty(value = "物料金额")
    private BigDecimal materialBalance;


    /**
     * 退回数量
     */
    @ExcelProperty(value = "退回数量")
    @ApiModelProperty(value = "退回数量")
    private BigDecimal returnCount;

    /**
     * 退回单价
     */
    @ExcelProperty(value = "退回单价")
    @ApiModelProperty(value = "退回单价")
    private BigDecimal returnUnitPrice;
    /**
     * 退回金额
     */
    @ExcelProperty(value = "退回金额")
    @ApiModelProperty(value = "退回金额")
    private BigDecimal returnBalance;

    /**
     * 税金
     */
    @ExcelProperty(value = "税金")
    @ApiModelProperty(value = "税金")
    private BigDecimal taxBalance;

    /**
     * 其他金额
     */
    @ExcelProperty(value = "其他金额")
    @ApiModelProperty(value = "其他金额")
    private BigDecimal otherBalance;

    /**
     * 打款金额
     */
    @ExcelProperty(value = "打款金额")
    @ApiModelProperty(value = "打款金额")
    private BigDecimal payBalance;

    /**
     * 账面余额
     */
    @ExcelProperty(value = "账面余额")
    @ApiModelProperty(value = "账面余额")
    private BigDecimal bookBalance;

/*    @ExcelProperty(value = "打款日期",converter = LocalDateConverter.class)
    @ApiModelProperty(value = "打款日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date payDate;*/

    @ExcelProperty(value = "运费")
    @ApiModelProperty(value = "运费")
    private BigDecimal freight;

    @ApiModelProperty(value = "经办人英文名")
    private String operatorBy;

    @ExcelProperty(value = "经办人")
    @ApiModelProperty(value = "经办人名字")
    private String operatorByName;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 付款类型  0-入款 1-出款
     */
    
    @ApiModelProperty(value = "付款类型  0-入款 1-出款")
    private String payType;
    
    @ApiModelProperty(value = "付款类型")
    private String payTypeName;


    
    @ApiModelProperty(value = "是否默认值 Y-是；N-否  前端固定值N")
    private String isDefault;

    @ApiModelProperty(value = "厂区编号")
    private String factoryCode;






    /**
     * 创建者
     */
    
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

}
