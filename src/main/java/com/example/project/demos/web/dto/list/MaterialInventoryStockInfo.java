package com.example.project.demos.web.dto.list;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-23 14:35:27
 * 实时库存   存放地点及相应库存类
 */
@Data
public class MaterialInventoryStockInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "厂区/仓库名称")
	private String stockName;

	/**
	 * 库存
	 */
	@ApiModelProperty(value = "库存")
	private BigDecimal inventoryNum;

}
