package com.example.project.demos.web.dto.list;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.example.project.demos.web.utils.LocalDateConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-25 15:30:37
 */
@Data
public class SupplyCustomerPayInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
	@ApiModelProperty(value = "主键")
	@ExcelIgnore
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	/**
	 * 来料入库主键
	 */
	@ExcelIgnore
	private Long incomeId;
	/**
	 * 客户编号
	 */
	@ExcelProperty(value = "客户编号")
	@ApiModelProperty(value = "客户编号")
	private String customerCode;
	@ExcelProperty(value = "客户名称")
	@ApiModelProperty(value = "客户名称")
	private String customerName;

	/**
	 * 物料编号
	 */
	@ExcelProperty(value = "物料编号")
	@ApiModelProperty(value = "物料编号")
	private String materialCode;
	@ExcelProperty(value = "物料名称")
	@ApiModelProperty(value = "物料名称")
	private String materialName;

	/**
	 * 型号
	 */
	@ExcelIgnore
	@ApiModelProperty(value = "型号")
	private String model;
	@ExcelProperty(value = "型号")
	@ApiModelProperty(value = "型号")
	private String modelName;

	/**
	 * 单位
	 */
	@ExcelIgnore
	@ApiModelProperty(value = "单位")
	private String unit;
	@ExcelProperty(value = "单位")
	@ApiModelProperty(value = "单位")
	private String unitName;
	/**
	 * 单价
	 */
	@ExcelProperty(value = "单价")
	@ApiModelProperty(value = "单价")
	private BigDecimal unitPrice;
	/**
	 * 来料日期
	 */
	@ExcelProperty(value = "日期",converter = LocalDateConverter.class)
	@ApiModelProperty(value = "日期")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date incomeDate;
	/**
	 * 来料数量
	 */
	@ExcelProperty(value = "数量")
	@ApiModelProperty(value = "数量")
	private BigDecimal incomeCount;
	/**
	 * 总金额
	 */
	@ExcelProperty(value = "总金额")
	@ApiModelProperty(value = "总金额")
	private BigDecimal tollAmount;

	@ExcelIgnore
	@ApiModelProperty(value = "业务类型编码")
	private String functionType;
	@ExcelProperty(value = "业务类型")
	@ApiModelProperty(value = "业务类型名称")
	private String functionTypeName;
	@ExcelIgnore
	@ApiModelProperty(value = "经办人英文名")
	private String operator;

	@ExcelProperty(value = "经办人")
	@ApiModelProperty(value = "经办人姓名")
	private String operatorName;
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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
	/**
	 * 备注
	 */
	@ExcelProperty(value = "备注")
	@ApiModelProperty(value = "备注")
	private String remark;

}
