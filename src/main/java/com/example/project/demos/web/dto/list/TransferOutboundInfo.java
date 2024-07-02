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
 * 调拨出库表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-11 16:09:49
 */
@Data
public class TransferOutboundInfo implements Serializable {
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
	 * 调拨数量
	 */
	@ExcelProperty(value = "调拨数量")
	@ApiModelProperty(value = "调拨数量")
	private BigDecimal transferCount;
	/**
	 * 调拨类型 0-原材料；1-产量
	 */
	@ExcelIgnore
	@ApiModelProperty(value = "调拨类型 0-原材料；1-产量")
	private String transferType;
	@ExcelProperty(value = "调拨类型")
	@ApiModelProperty(value = "调拨类型")
	private String transferTypeName;

	@ExcelProperty(value = "调拨日期")
	@ApiModelProperty(value = "调拨日期")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date transferDate;
	/**
	 * 单据号
	 */
	@ExcelProperty(value = "单据号")
	@ApiModelProperty(value = "单据号")
	private String billNo;
	@ExcelIgnore
	@ApiModelProperty(value = "确认人英文名")
	private String confirmUser;
	@ExcelProperty(value = "确认人")
	@ApiModelProperty(value = "确认人姓名")
	private String confirmUserName;
	/**
	 * 单据(确认)状态
	 */
	@ExcelIgnore
	@ApiModelProperty(value = "单据(确认)状态 0-未确认 ;1-已确认；2-拒绝确认")
	private String confirmState;
	@ExcelProperty(value = "确认状态")
	@ApiModelProperty(value = "单据(确认)状态")
	private String confirmStateName;
	/**
	 * 单据(确认)状态时间
	 */
	@ExcelProperty(value = "确认时间")
	@ApiModelProperty(value = "单据(确认)时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date confirmTime;
	/**
	 * 单据(确认)理由
	 */
	@ExcelProperty(value = "确认理由")
	@ApiModelProperty(value = "单据(确认)理由")
	private String confirmOpinion;

	/**
	 * 调出厂(仓库)编号
	 */
	@ExcelIgnore
	@ApiModelProperty(value = "调出厂(仓库)编号")
	private String outCode;
	@ExcelProperty(value = "调出方")
	@ApiModelProperty(value = "调出厂(仓库)")
	private String outName;
	/**
	 * 调入厂（仓库）编号
	 */
	@ExcelIgnore
	@ApiModelProperty(value = "调入厂（仓库）编号")
	private String inCode;
	@ExcelProperty(value = "调入方")
	@ApiModelProperty(value = "调入厂（仓库）")
	private String inName;
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
