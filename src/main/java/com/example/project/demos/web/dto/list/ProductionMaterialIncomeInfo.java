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
 * @date 2024-05-11 09:25:49
 */
@Data
public class ProductionMaterialIncomeInfo implements Serializable {
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
	@ExcelProperty(value = "物料编号")
	@ApiModelProperty(value = "物料编号")
	private String materialCode;

	@ExcelProperty(value = "物料名称")
	@ApiModelProperty(value = "物料名称")
	private String materialName;

	/**
	 * 型号
	 */
	@ApiModelProperty(value = "型号")
	private String model;

	@ExcelProperty(value = "型号")
	@ApiModelProperty(value = "型号")
	private String modelName;
	/**
	 * 单位
	 */
	@ApiModelProperty(value = "单位")
	private String unit;

	@ExcelProperty(value = "单位")
	@ApiModelProperty(value = "单位")
	private String unitName;

	/**
	 * 入库数量
	 */
	@ExcelProperty(value = "入库数量")
	@ApiModelProperty(value = "入库数量")
	private BigDecimal incomeNum;
	/**
	 * 生产员工
	 */
	@ApiModelProperty(value = "生产员工")
	private String producer;
	@ExcelProperty(value = "生产员工")
	@ApiModelProperty(value = "生产员工")
	private String producerName;
	/**
	 * 生产日期
	 */
	@ExcelProperty(value = "生产日期")
	@ApiModelProperty(value = "生产日期")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date produceTime;
	/**
	 * 入库方编号
	 */
	@ApiModelProperty(value = "入库方编号")
	private String inCode;
	@ExcelProperty(value = "入库方")
	@ApiModelProperty(value = "入库方名称")
	private String inName;
	/**
	 * 审批状态
	 */
	@ApiModelProperty(value = "审批状态")
	private String approveState;
	/**
	 * 单据状态
	 */
	@ApiModelProperty(value = "单据状态")
	private String billState;

	@ApiModelProperty(value = "班组编号")
	private String dutyCode;
	@ExcelProperty(value = "班组")
	@ApiModelProperty(value = "班组名称")
	private String dutyName;
	@ApiModelProperty(value = "机器编号")
	private String machineCode;
	@ExcelProperty(value = "机器号")
	@ApiModelProperty(value = "机器名称")
	private String machineName;

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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date updateTime;
	/**
	 * 备注
	 */
	@ExcelProperty(value = "备注")
	@ApiModelProperty(value = "备注")
	private String remark;

}
