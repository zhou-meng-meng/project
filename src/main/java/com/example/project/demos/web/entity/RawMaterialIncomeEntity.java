package com.example.project.demos.web.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 原材料来料入库表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-10 14:54:43
 */
@Data
@TableName("raw_material_income")
public class RawMaterialIncomeEntity implements Serializable {
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
	 * 型号
	 */
	private String model;
	/**
	 * 单位
	 */
	private String unit;
	/**
	 * 单价
	 */
	private BigDecimal unitPrice;
	/**
	 * 供货商编号
	 */
	private String supplyerCode;
	/**
	 * 厂区编号
	 */
	private String factoryCode;
	/**
	 * 单据号
	 */
	private String billNo;
	/**
	 * 进货人
	 */
	private String materialBuyer;
	/**
	 * 进货时间
	 */
	private Date materialBuytime;
	/**
	 * 审批状态
	 */
	private String approveState;
	/**
	 * 单据状态
	 */
	private String billState;
	/**
	 * 总数量
	 */
	private BigDecimal count;
	/**
	 * 总金额
	 */
	private BigDecimal tollAmout;
	/**
	 * 创建人
	 */
	private String createBy;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改人
	 */
	private String updateBy;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 备注
	 */
	private String remark;

}
