package com.example.project.demos.web.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 物料产出装袋表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-09 09:26:13
 */
@Data
@TableName("material_package")
public class MaterialPackageEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
	@TableId
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	/**
	 * 厂区编码
	 */
	private String factoryCode;
	/**
	 * 机子号
	 */
	private String machineCode;
	/**
	 * 日期
	 */
	private Date packageDate;
	/**
	 * 班次
	 */
	private String dutyCode;
	/**
	 * 锅数
	 */
	private BigDecimal potNum;
	private BigDecimal packageWeight;
	/**
	 * 合计重量
	 */
	private BigDecimal tollWeight;
	/**
	 * 应出袋数
	 */
	private BigDecimal shouldNum;
	/**
	 * 实际袋数
	 */
	private BigDecimal actualNum;
	/**
	 * 差额
	 */
	private BigDecimal balanceNum;
	/**
	 * 录入人
	 */
	private String createBy;
	/**
	 * 录入时间
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
