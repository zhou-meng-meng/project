package com.example.project.demos.web.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
@TableName("material_info")
public class MaterialInfoEntity implements Serializable {
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
	private String code;
	/**
	 * 物料名称
	 */
	private String name;
	/**
	 * 型号
	 */
	private String model;

	/**
	 * 单位
	 */
	private String unit;

	/**
	 * 物料类型  0-原材料；1-销售产品
	 */
	private String type;
	/**
	 * 供应商编号（来料使用）
	 */
	private String supplyerCode;
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
