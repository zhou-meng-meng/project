package com.example.project.demos.web.dto.list;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 菜单权限表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-04-24 13:42:58
 */
@Data
public class SysMenuInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	/**
	 * 菜单ID
	 */
	@ApiModelProperty(value = "菜单ID")
	private String menuId;
	/**
	 * 菜单名称
	 */
	@ApiModelProperty(value = "菜单名称")
	private String menuName;
	/**
	 * 父菜单ID
	 */
	@ApiModelProperty(value = "父菜单ID")
	private String parentId;
	/**
	 * 父菜单
	 */
	@ApiModelProperty(value = "父菜单")
	private String parentName;
	/**
	 * 显示顺序
	 */
	@ApiModelProperty(value = "显示顺序")
	private Integer orderNum;
	/**
	 * 路由地址
	 */
	@ApiModelProperty(value = "路由地址")
	private String path;
	/**
	 * 组件路径
	 */
	@ApiModelProperty(value = "组件路径")
	private String component;
	/**
	 * 路由参数
	 */
	@ApiModelProperty(value = "路由参数")
	private String query;
	/**
	 * 菜单状态（0显示 1隐藏）
	 */
	@ApiModelProperty(value = "菜单状态（0显示 1隐藏）")
	private String visible;
	/**
	 * 菜单状态（0正常 1停用）
	 */
	@ApiModelProperty(value = "菜单使用状态（0正常 1停用）")
	private String status;
	@ApiModelProperty(value = "菜单使用状态")
	private String statusName;

	/** 子菜单 */
	private List<SysMenuInfo> children = new ArrayList<SysMenuInfo>();
	/**
	 * 创建者
	 */
	@ApiModelProperty(value = "创建者")
	private String createBy;
	@ApiModelProperty(value = "创建人名称")
	private String createByName;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/**
	 * 更新者
	 */
	@ApiModelProperty(value = "修改人")
	private String updateBy;
	@ApiModelProperty(value = "修改人名称")
	private String updateByName;
	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;

}
