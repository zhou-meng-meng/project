package com.example.project.demos.web.dto.list;

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
import java.util.List;

/**
 * 
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-23 14:35:27
 */
@Data

public class MaterialInventoryInfo implements Serializable {
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

	@ApiModelProperty(value = "各厂区/仓库库存集合")
	private List<MaterialInventoryStockInfo> stockInfo;

	@ApiModelProperty(value = "厂区/仓库编号")
	private String stockCode;
	@ApiModelProperty(value = "厂区/仓库名称")
	private String stockName;
	@ApiModelProperty(value = "库存")
	private BigDecimal inventoryNum;

	/**
	 * 合计
	 */
	@ApiModelProperty(value = "合计")
	private BigDecimal tollNum;
	@ApiModelProperty(value = "最后更新时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateTime;

	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty(value = "分组标识")
	private String groupBy;

}
