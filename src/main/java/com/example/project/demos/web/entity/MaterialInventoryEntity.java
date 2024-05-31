package com.example.project.demos.web.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-23 14:35:27
 */
@Data
@TableName("material_inventory")
public class MaterialInventoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
	@TableId
	private Long id;
	/**
	 * 物料编号
	 */
	private String materialCode;
	/**
	 * 厂区/仓库编号
	 */
	private String stockCode;
	/**
	 * 库存数量
	 */
	private BigDecimal inventoryNum;
	private Date lastUpdateTime;
	/**
	 * 备注
	 */
	private String remark;

}
