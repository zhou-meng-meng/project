package com.example.project.demos.web.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-11 09:25:49
 */
@Data
@TableName("production_material_income")
public class ProductionMaterialIncomeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
	@TableId
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	/**
	 * 物料编号
	 */
	private String materialCode;
	/**
	 * 入库数量
	 */
	private BigDecimal incomeNum;
	/**
	 * 生产员工
	 */
	private String producer;
	/**
	 * 生产日期
	 */
	private Date produceTime;
	/**
	 * 入库方编号
	 */
	private String inCode;


	private String dutyCode;

	private String machineCode;

	/**
	 * 审批状态
	 */
	private String approveState;
	/**
	 * 单据状态
	 */
	private String billState;
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
	 * 修改日期
	 */
	private Date updateTime;
	/**
	 * 备注
	 */
	private String remark;

}
