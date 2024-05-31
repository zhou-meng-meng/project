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
 * @date 2024-05-11 11:13:27
 */
@Data
public class RebuildInboundInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
	@ApiModelProperty(value = "自增主键")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	/**
	 * 物料编号
	 */
	@ApiModelProperty(value = "物料编号")
	private String materialCode;
	@ApiModelProperty(value = "物料名称")
	private String materialName;
	@ApiModelProperty(value = "型号")
	private String model;
	@ApiModelProperty(value = "型号")
	private String modelName;

	@ApiModelProperty(value = "单位")
	private String unit;
	@ApiModelProperty(value = "单位名称")
	private String unitName;
	/**
	 * 重造出库数量
	 */
	@ApiModelProperty(value = "重造出库数量")
	private BigDecimal rebuildCount;
	/**
	 * 重造时间
	 */
	@ApiModelProperty(value = "重造日期")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date rebuildDate;
	/**
	 * 入库方编号
	 */
	@ApiModelProperty(value = "入库方编号")
	private String inCode;
	@ApiModelProperty(value = "入库方名称")
	private String inName;
	@ApiModelProperty(value = "班组编号")
	private String dutyCode;
	@ApiModelProperty(value = "班组名称")
	private String dutyName;
	@ApiModelProperty(value = "机器号")
	private String machineCode;
	@ApiModelProperty(value = "机器号")
	private String machineName;

	/**
	 * 单据号
	 */
	@ApiModelProperty(value = "单据号")
	private String billNo;
	/**
	 * 单据状态
	 */
	@ApiModelProperty(value = "单据状态")
	private String billState;
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
