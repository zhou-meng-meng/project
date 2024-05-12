package com.example.project.demos.web.dto.list;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
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
	private Long id;
	/**
	 * 物料编号
	 */
	@ApiModelProperty(value = "物料编号")
	private String materialCode;
	@ApiModelProperty(value = "物料名称")
	private String materialName;
	/**
	 * 调拨数量
	 */
	private BigDecimal transferCount;
	/**
	 * 调拨类型 0-原材料；1-产量
	 */
	@ApiModelProperty(value = "调拨类型 0-原材料；1-产量")
	private String transferType;
	@ApiModelProperty(value = "调拨类型")
	private String transferTypeName;

	@ApiModelProperty(value = "调拨日期")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date transferDate;
	/**
	 * 单位
	 */
	@ApiModelProperty(value = "单位")
	private String unit;
	/**
	 * 单据号
	 */
	@ApiModelProperty(value = "单据号")
	private String billNo;
	/**
	 * 单据(确认)状态
	 */
	@ApiModelProperty(value = "单据(确认)状态 0-未确认 ;1-已确认；2-拒绝确认")
	private String confirmState;
	@ApiModelProperty(value = "单据(确认)状态")
	private String confirmStateName;

	/**
	 * 单据(确认)状态时间
	 */
	@ApiModelProperty(value = "单据(确认)状态时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date confirmTime;

	/**
	 * 单据(确认)理由
	 */
	@ApiModelProperty(value = "单据(确认)理由")
	private String confirmOpinion;

	/**
	 * 调出厂(仓库)编号
	 */
	@ApiModelProperty(value = "调出厂(仓库)编号")
	private String outCode;
	@ApiModelProperty(value = "调出厂(仓库)")
	private String outName;
	/**
	 * 调入厂（仓库）编号
	 */
	@ApiModelProperty(value = "调入厂（仓库）编号")
	private String inCode;
	@ApiModelProperty(value = "调入厂（仓库）")
	private String inName;
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