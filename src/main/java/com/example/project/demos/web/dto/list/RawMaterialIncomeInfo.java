package com.example.project.demos.web.dto.list;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 原材料来料入库表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-10 14:54:43
 */
@Data
public class RawMaterialIncomeInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
	@ApiModelProperty(value = "主键")
	@ExcelIgnore
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
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
	private String unitPrice;

	/**
	 * 总数量
	 */
	@ExcelProperty(value = "总数量")
	@ApiModelProperty(value = "总数量")
	private BigDecimal count;
	/**
	 * 总金额
	 */
	@ExcelProperty(value = "总金额")
	@ApiModelProperty(value = "总金额")
	private BigDecimal tollAmount;

	/**
	 * 供货商编号
	 */
	@ExcelIgnore
	@ApiModelProperty(value = "供货商编号")
	private String supplyerCode;

	@ExcelProperty(value = "供货商名称")
	@ApiModelProperty(value = "供货商名称")
	private String supplyerName;
	/**
	 * 入库方编号
	 */
	@ExcelIgnore
	@ApiModelProperty(value = "入库方编号")
	private String inCode;

	@ExcelProperty(value = "入库方名称")
	@ApiModelProperty(value = "入库方名称")
	private String inName;
	/**
	 * 单据号
	 */

	@ExcelProperty(value = "单据号")
	@ApiModelProperty(value = "单据号")
	private String billNo;
	/**
	 * 进货人
	 */
	@ExcelIgnore
	@ApiModelProperty(value = "进货人")
	private String materialBuyer;

	@ExcelProperty(value = "进货人")
	@ApiModelProperty(value = "进货人")
	private String materialBuyerName;
	/**
	 * 进货时间
	 */
	@ExcelProperty(value = "进货时间")
	@ApiModelProperty(value = "进货时间")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date materialBuytime;

	@ExcelIgnore
	@ApiModelProperty(value = "审核状态人英文名")
	private String approveUser;

	@ExcelProperty(value = "审核人")
	@ApiModelProperty(value = "审核人")
	private String approveUserName;
	/**
	 * 审批状态
	 */
	@ExcelIgnore
	@ApiModelProperty(value = "审核状态")
	private String approveState;

	@ExcelProperty(value = "审核状态")
	@ApiModelProperty(value = "审核状态")
	private String approveStateName;

	@ExcelProperty(value = "审核时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date approveTime;

	@ExcelProperty(value = "审核意见")
	@ApiModelProperty(value = "审核意见")
	private String approveOpinion;

	/**
	 * 单据状态
	 */
	@ExcelIgnore
	@ApiModelProperty(value = "单据状态")
	private String billState;

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
