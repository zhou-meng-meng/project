package com.example.project.demos.web.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 工厂维护表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-04-24 13:42:58
 */
@Data
@TableName("sys_factory")
public class SysFactoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 厂区编号
	 */
	@TableId
	private String id;
	/**
	 * 厂区名称
	 */
	private String name;
	/**
	 * 厂区详细地址
	 */
	private String address;
	/**
	 * 厂区负责人
	 */
	private String manage;
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
