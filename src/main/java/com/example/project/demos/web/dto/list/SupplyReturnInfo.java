package com.example.project.demos.web.dto.list;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @date 2024-05-23 10:56:52
 */
@Data
public class SupplyReturnInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
	@ApiModelProperty(value = "自增主键")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
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

	@ApiModelProperty(value = "型号")
	private String model;
	@ExcelProperty(value = "型号")
	@ApiModelProperty(value = "型号")
	private String modelName;

	@ApiModelProperty(value = "单位")
	private String unit;
	@ExcelProperty(value = "单位")
	@ApiModelProperty(value = "单位名称")
	private String unitName;

	/**
	 * 单价
	 */
	@ExcelProperty(value = "单价")
	@ApiModelProperty(value = "单价")
	private BigDecimal unitPrice;
	/**
	 * 退回数量
	 */
	@ExcelProperty(value = "退回数量")
	@ApiModelProperty(value = "退回数量")
	private BigDecimal returnCount;
	/**
	 * 总金额
	 */
	@ExcelProperty(value = "总金额")
	@ApiModelProperty(value = "总金额")
	private BigDecimal tollAmount;
	@ApiModelProperty(value = "审核人")
	private String approveUser;
	@ExcelProperty(value = "审核人")
	@ApiModelProperty(value = "审核人")
	private String approveUserName;
	/**
	 * 审核状态
	 */
	@ApiModelProperty(value = "审核状态")
	private String approveState;
	@ExcelProperty(value = "审核状态")
	@ApiModelProperty(value = "审核状态")
	private String approveStateName;
	/**
	 * 审批意见
	 */
	@ExcelProperty(value = "审核意见")
	@ApiModelProperty(value = "审核意见")
	private String approveOpinion;
	@ExcelProperty(value = "审核时间")
	@ApiModelProperty(value = "审核时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date approveTime;
	/**
	 * 单据号
	 */
	@ExcelProperty(value = "单据号")
	@ApiModelProperty(value = "单据号")
	private String billNo;
	/**
	 * 单据状态
	 */
	@ApiModelProperty(value = "单据状态")
	private String billState;
	@ExcelProperty(value = "")
	@ApiModelProperty(value = "单据状态")
	private String billStateName;
	/**
	 * 退回方编号
	 */
	@ApiModelProperty(value = "退回方编号")
	private String outCode;
	@ExcelProperty(value = "退回方")
	@ApiModelProperty(value = "退回方名称")
	private String outName;
	/**
	 * 退回人
	 */
	@ApiModelProperty(value = "退回人")
	private String returnUser;
	@ExcelProperty(value = "退回人")
	@ApiModelProperty(value = "退回人")
	private String returnUserName;
	/**
	 * 退货时间
	 */
	@ExcelProperty(value = "退货时间")
	@ApiModelProperty(value = "退货时间")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date returnTime;
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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
	/**
	 * 备注
	 */
	@ExcelProperty(value = "备注")
	@ApiModelProperty(value = "备注")
	private String remark;

}
