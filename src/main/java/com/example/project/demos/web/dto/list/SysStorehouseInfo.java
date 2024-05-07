package com.example.project.demos.web.dto.list;

import io.swagger.annotations.ApiModelProperty;
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
public class SysStorehouseInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	private Long id;
	/**
	 * 仓库编号
	 */
	@ApiModelProperty(value = "仓库编号")
	private String code;
	/**
	 * 仓库名称
	 */
	@ApiModelProperty(value = "仓库名称")
	private String name;
	/**
	 * 仓库详细地址
	 */
	@ApiModelProperty(value = "仓库详细地址")
	private String address;
	/**
	 * 仓库负责人
	 */
	@ApiModelProperty(value = "仓库负责人")
	private String manage;

	@ApiModelProperty(value = "仓库负责人姓名")
	private String manageName;

	@ApiModelProperty(value = "仓库负责人电话")
	private String manageTel;

	@ApiModelProperty(value = "所属厂区编号")
	private String factoryCode;
	/**
	 * 创建者
	 */
	@ApiModelProperty(value = "创建者")
	private String createBy;
	@ApiModelProperty(value = "创建人名称")
	private String createName;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private Date createTime;
	/**
	 * 更新者
	 */
	@ApiModelProperty(value = "修改人")
	private String updateBy;
	@ApiModelProperty(value = "修改人名称")
	private String updateName;
	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间")
	private Date updateTime;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;

}
