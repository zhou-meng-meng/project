package com.example.project.demos.web.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 仓库维护表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-04-24 13:42:58
 */
@Data
@TableName("sys_storehouse")
public class SysStorehouseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 仓库编码
	 */
	@TableId
	private String id;
	/**
	 * 仓库名称
	 */
	private String name;
	/**
	 * 仓库详细地址
	 */
	private String address;
	/**
	 * 仓库负责人
	 */
	private String manage;
	/**
	 * 仓库负责人联系电话
	 */
	private String manageTel;
	/**
	 * 所属工厂编码
	 */
	private String factoryId;
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
