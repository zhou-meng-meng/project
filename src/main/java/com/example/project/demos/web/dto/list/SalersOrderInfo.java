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
 * 业务员下单表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-06-11 10:33:39
 */
@Data
public class SalersOrderInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
	@ExcelIgnore
	@ApiModelProperty(value = "主键")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

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
	 * 单据号
	 */
	@ExcelProperty(value = "单据号")
	@ApiModelProperty(value = "单据号")
	private String billCode;
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
	 * 联系人
	 */
	@ExcelProperty(value = "联系人")
	@ApiModelProperty(value = "联系人")
	private String customerLink;
	/**
	 * 客户联系方式
	 */
	@ExcelProperty(value = "客户联系方式")
	@ApiModelProperty(value = "客户联系方式")
	private String customerTel;
	/**
	 * 装车日期
	 */
	@ExcelProperty(value = "装车日期")
	@ApiModelProperty(value = "装车日期")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date loadDate;
	/**
	 * 装车数量
	 */
	@ExcelProperty(value = "装车数量")
	@ApiModelProperty(value = "装车数量")
	private BigDecimal loadNum;
	/**
	 * 单价
	 */
	@ExcelProperty(value = "单价")
	@ApiModelProperty(value = "单价")
	private BigDecimal unitPrice;
	@ExcelProperty(value = "总金额")
	@ApiModelProperty(value = "总金额")
	private BigDecimal tollAmount;
	/**
	 * 装车方式
	 */
	@ExcelIgnore
	@ApiModelProperty(value = "装车方式编码")
	private String loadType;

	@ExcelProperty(value = "装车方式")
	@ApiModelProperty(value = "装车方式")
	private String loadTypeName;

	@ExcelIgnore
	@ApiModelProperty(value = "销售员英文名")
	private String saler;
	@ExcelProperty(value = "销售员")
	@ApiModelProperty(value = "销售员姓名")
	private String salerName;
	/**
	 * 出库方编码
	 */
	@ExcelIgnore
	@ApiModelProperty(value = "出库方编码")
	private String outCode;
	@ExcelProperty(value = "出库方")
	@ApiModelProperty(value = "出库方名称")
	private String outName;
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
	 * 审核人
	 */
	@ExcelIgnore
	@ApiModelProperty(value = "审核人")
	private String approveUser;
	@ExcelProperty(value = "审核人")
	@ApiModelProperty(value = "审核人姓名")
	private String approveUserName;
	/**
	 * 审核意见
	 */
	@ExcelProperty(value = "审核意见")
	@ApiModelProperty(value = "审核意见")
	private String approveOpinion;
	/**
	 * 审核时间
	 */
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
	 * 单据状态
	 */
	@ExcelIgnore
	@ApiModelProperty(value = "单据状态")
	private String billState;

	@ExcelProperty(value = "单据状态")
	@ApiModelProperty(value = "单据状态")
	private String billStateName;

	@ExcelIgnore
	@ApiModelProperty(value = "冲销人")
	private String chargeoffUser;

	@ExcelProperty(value = "冲销人")
	@ApiModelProperty(value = "冲销人姓名")
	private String chargeoffUserName;

	@ExcelProperty(value = "冲销时间")
	@ApiModelProperty(value = "冲销时间")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date chargeoffTime;

	@ExcelProperty(value = "冲销意见")
	@ApiModelProperty(value = "冲销意见")
	private String chargeoffOpinion;

	/**
	 * 汇款
	 */
	@ExcelProperty(value = "汇款")
	@ApiModelProperty(value = "汇款")
	private BigDecimal remit;
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
