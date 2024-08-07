package com.example.project.demos.web.dto.list;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.example.project.demos.web.utils.LocalDateConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-11 09:25:49
 */
@ExcelIgnoreUnannotated
@Data
public class ProductionMaterialIncomeInfo implements Serializable {
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
	 * 入库数量
	 */
	@ExcelProperty(value = "入库数量")
	@ApiModelProperty(value = "入库数量")
	private BigDecimal incomeNum;

	/**
	 * 生产日期
	 */
	@ExcelProperty(value = "生产日期",converter = LocalDateConverter.class)
	@ApiModelProperty(value = "生产日期")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date produceTime;

	@ApiModelProperty(value = "员工生产信息列表")
	List<ProductProducerInfo> producerInfoList;

	@ExcelProperty(value = "生产员工")
	@ApiModelProperty(value = "生产员工姓名")
	private String producerName;

	@ExcelProperty(value = "员工产量")
	@ApiModelProperty(value = "员工产量")
	private BigDecimal producerNum;

	/**
	 * 入库方编号
	 */
	@ExcelIgnore
	@ApiModelProperty(value = "入库方编号")
	private String inCode;

	@ExcelProperty(value = "入库方")
	@ApiModelProperty(value = "入库方名称")
	private String inName;

	@ExcelProperty(value = "单据号")
	@ApiModelProperty(value = "单据号")
	private String billNo;

	/**
	 * 审批状态
	 */
	@ExcelIgnore
	@ApiModelProperty(value = "审核状态")
	private String approveState;
	/**
	 * 单据状态
	 */
	@ExcelIgnore
	@ApiModelProperty(value = "单据状态")
	private String billState;

	@ExcelIgnore
	@ApiModelProperty(value = "班组编号")
	private String dutyCode;
	@ExcelProperty(value = "班组")
	@ApiModelProperty(value = "班组名称")
	private String dutyName;

	@ExcelIgnore
	@ApiModelProperty(value = "机器编号")
	private String machineCode;
	@ExcelProperty(value = "机器号")
	@ApiModelProperty(value = "机器名称")
	private String machineName;

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
