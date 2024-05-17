package com.example.project.demos.web.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 字典数据表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-04-24 13:42:58
 */
@Data
@TableName("sys_dict_data")
public class SysDictDataEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 字典编码
	 */
	@TableId
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

	/**
	 * 字典类型
	 */
	private String dictType;


	/**
	 * 字典编码
	 */
	private String dictCode;

	/**
	 * 字典键值
	 */
	private String dictValue;
	/**
	 * 字典排序
	 */
	private Integer dictSort;

	/**
	 * 是否默认（Y是 N否）
	 */
	private String isDefault;
	/**
	 * 状态（0正常 1停用）
	 */
	private String status;
	/**
	 * 创建者
	 */
	private String createBy;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新者
	 */
	private String updateBy;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 备注
	 */
	private String remark;

}
