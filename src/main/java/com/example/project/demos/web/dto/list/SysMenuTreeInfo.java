package com.example.project.demos.web.dto.list;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单树形结构
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-04-24 13:42:58
 */
@Data
public class SysMenuTreeInfo implements Serializable {
	private static final long serialVersionUID = 1L;

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
	 * 显示顺序
	 */
	@ApiModelProperty(value = "显示顺序")
	private String orderNum;
	/**
	 * 路由地址
	 */
	@ApiModelProperty(value = "路由地址")
	private String path;

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

	/** 子节点 */
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<SysMenuTreeInfo> children;


	public SysMenuTreeInfo() {};

	public SysMenuTreeInfo(SysMenuInfo menu) {
		this.menuId = menu.getMenuId();
		this.menuName = menu.getMenuName();
		this.orderNum = menu.getOrderNum();
		this.path = menu.getPath();
		this.visible = menu.getVisible();
		this.status = menu.getStatus();
		this.children = menu.getChildren().stream().map(SysMenuTreeInfo::new).collect(Collectors.toList());
	}

}
