package com.example.project.demos.web.dto.sysMenu;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EditDTO {
    /**
     * 字典主键
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

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
}
