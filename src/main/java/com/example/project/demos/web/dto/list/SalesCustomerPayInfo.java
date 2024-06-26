package com.example.project.demos.web.dto.list;

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
public class SalesCustomerPayInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
	@ApiModelProperty(value = "主键")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	/**
	 * 销售出库主键
	 */
	private Long saleId;
	/**
	 * 客户编号
	 */
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

	/**
	 * 型号
	 */
	@ApiModelProperty(value = "型号")
	private String model;
	@ApiModelProperty(value = "型号")
	private String modelName;

	/**
	 * 单位
	 */
	@ApiModelProperty(value = "单位")
	private String unit;
	@ApiModelProperty(value = "单位")
	private String unitName;
	/**
	 * 单价
	 */
	@ApiModelProperty(value = "单价")
	private BigDecimal unitPrice;
	/**
	 * 销售日期
	 */
	@ApiModelProperty(value = "销售日期")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date saleDate;
	/**
	 * 销售数量
	 */
	@ApiModelProperty(value = "销售数量")
	private BigDecimal saleCount;
	/**
	 * 总金额
	 */
	@ApiModelProperty(value = "总金额")
	private BigDecimal tollAmount;

	@ApiModelProperty(value = "业务类型编码")
	private String functionType;
	@ApiModelProperty(value = "业务类型名称")
	private String functionTypeName;

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

}
