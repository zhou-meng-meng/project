package com.example.project.demos.web.dto.list;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 物料信息维护表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-08 15:30:25
 */
@Data
public class MaterialInfo implements Serializable {
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
	private String code;
	/**
	 * 物料名称
	 */
	@ExcelProperty(value = "物料名称")
	@ApiModelProperty(value = "物料名称")
	private String name;
	/**
	 * 型号
	 */
	@ExcelIgnore
	@ApiModelProperty(value = "型号编号")
	private String model;

	/**
	 * 型号名称
	 */
	@ExcelProperty(value = "型号")
	@ApiModelProperty(value = "型号名称")
	private String modelName;

	/**
	 * 单位
	 */
	@ExcelIgnore
	@ApiModelProperty(value = "单位")
	private String unit;

	/**
	 * 单位名称
	 */
	@ExcelProperty(value = "单位")
	@ApiModelProperty(value = "单位名称")
	private String unitName;

	/**
	 * 物料类型  0-原材料；1-销售产品
	 */
	@ExcelIgnore
	@ApiModelProperty(value = "物料类型  0-原材料；1-销售产品")
	private String type;

	@ExcelProperty(value = "物料类型")
	@ApiModelProperty(value = "物料类型  0-原材料；1-销售产品")
	private String typeName;
	/**
	 * 供应商编号（来料使用）
	 */
	@ExcelProperty(value = "供应商编号（来料使用）")
	@ApiModelProperty(value = "供应商编号（来料使用）")
	private String supplyerCode;
	@ExcelProperty(value = "供应商（来料使用）")
	@ApiModelProperty(value = "供应商名称（来料使用）")
	private String supplyerName;
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
