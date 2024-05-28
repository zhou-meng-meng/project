package com.example.project.demos.web.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 物料用量表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-08 17:08:07
 */
@Data
@TableName("material_dosage")
public class MaterialDosageEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
	@TableId
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	/**
	 * 厂区编号
	 */
	private String factoryCode;
	/**
	 * 班组日期
	 */
	private Date dutyDate;
	/**
	 * 班组 0-白班 1-中班 2-夜班
	 */
	private String dutyCode;
	/**
	 * 机器编号
	 */
	private String machineCode;
	/**
	 * 进货单号
	 */
	private String stockBillNo;
	/**
	 * 单号
	 */
	private String billNo;
	/**
	 * 磨粉棒重量
	 */
	private Long grindingWeight;
	/**
	 * 机器磅重量
	 */
	private Long machineWeight;
	/**
	 * 差额（磨粉棒重量-机器磅重量）
	 */
	private Long differentWeight;
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
