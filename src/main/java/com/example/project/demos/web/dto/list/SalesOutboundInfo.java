package com.example.project.demos.web.dto.list;

import com.alibaba.excel.annotation.ExcelIgnore;
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
 * 销售出库表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-24 14:38:26
 */
@Data
public class SalesOutboundInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
	@ApiModelProperty(value = "自增主键")
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

	@ExcelIgnore
	@ApiModelProperty(value = "型号")
	private String model;
	@ExcelProperty(value = "型号")
	@ApiModelProperty(value = "型号")
	private String modelName;

	@ExcelIgnore
	@ApiModelProperty(value = "单位")
	private String unit;
	@ExcelProperty(value = "单位")
	@ApiModelProperty(value = "单位名称")
	private String unitName;

	/**
	 * 出库数量
	 */
	@ExcelProperty(value = "出库数量")
	@ApiModelProperty(value = "出库数量")
	private BigDecimal outCount;
	/**
	 * 单价
	 */
	@ExcelProperty(value = "单价")
	@ApiModelProperty(value = "单价")
	private BigDecimal unitPrice;
	/**
	 * 总金额
	 */
	@ExcelProperty(value = "总金额")
	@ApiModelProperty(value = "总金额")
	private BigDecimal tollAmount;

	@ExcelIgnore
	@ApiModelProperty(value = "购货客户编号")
	private String customerCode;
	@ExcelProperty(value = "购货客户名称")
	@ApiModelProperty(value = "购货客户名称")
	private String customerName;
	/**
	 * 出库方编号
	 */
	@ExcelIgnore
	@ApiModelProperty(value = "出库方编号")
	private String outCode;
	@ExcelProperty(value = "出库方名称")
	@ApiModelProperty(value = "出库方名称")
	private String outName;
	/**
	 * 销售员英文名
	 */
	@ExcelIgnore
	@ApiModelProperty(value = "销售员英文名")
	private String saler;
	@ExcelProperty(value = "销售员名称")
	@ApiModelProperty(value = "销售员名称")
	private String salerName;
	/**
	 * 销售时间
	 */
	@ExcelProperty(value = "销售时间")
	@ApiModelProperty(value = "销售时间")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date saleTime;

	/**
	 * 单据号
	 */
	@ExcelProperty(value = "单据号")
	@ApiModelProperty(value = "单据号")
	private String billNo;
	/**
	 * 单据状态
	 */
	@ExcelIgnore
	@ApiModelProperty(value = "单据状态")
	private String billState;

	@ExcelProperty(value = "单据状态")
	@ApiModelProperty(value = "单据状态")
	private String billStateName;
	/**
	 * 运输方式
	 */
	@ExcelIgnore
	@ApiModelProperty(value = "运输方式")
	private String transportType;

	@ExcelProperty(value = "运输方式")
	@ApiModelProperty(value = "运输方式")
	private String transportTypeName;
	/**
	 * 运费
	 */
	@ExcelProperty(value = "运费")
	@ApiModelProperty(value = "运费")
	private BigDecimal freight;
	/**
	 * 回款金额
	 */
	@ExcelProperty(value = "回款金额")
	@ApiModelProperty(value = "回款金额")
	private BigDecimal receivePayment;
	/**
	 * 回款时间
	 */
	@ExcelProperty(value = "回款时间")
	@ApiModelProperty(value = "回款时间")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date receivePaymentTime;

	@ExcelIgnore
	@ApiModelProperty(value = "审核人")
	private String approveUser;

	@ExcelProperty(value = "审核人姓名")
	@ApiModelProperty(value = "审核人姓名")
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
	 * 确认状态
	 */
	@ExcelIgnore
	@ApiModelProperty(value = "确认状态编码")
	private String confirmState;

	@ExcelProperty(value = "确认状态")
	@ApiModelProperty(value = "确认状态")
	private String confirmStateName;
	/**
	 * 确认人
	 */
	@ExcelIgnore
	@ApiModelProperty(value = "确认人")
	private String confirmUser;
	@ExcelProperty(value = "确认人")
	@ApiModelProperty(value = "确认人")
	private String confirmUserName;
	/**
	 * 确认意见
	 */
	@ExcelProperty(value = "确认意见")
	@ApiModelProperty(value = "确认意见")
	private String confirmOpinion;
	/**
	 * 确认时间
	 */
	@ExcelProperty(value = "确认时间")
	@ApiModelProperty(value = "确认时间")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date confirmTime;

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
