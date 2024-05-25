package com.example.project.demos.web.dto.sysRole;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AddDTO {

    /**
     * 角色ID
     */
    @ApiModelProperty(value = "角色ID")
    private String roleId;
    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称")
    private String roleName;
    /**
     * 显示顺序
     */
    @ApiModelProperty(value = "显示顺序")
    private Integer roleSort;

    /**
     * 角色权限类型集合
     */
    @ApiModelProperty(value = "角色权限类型集合")
    private List<String> authorityType;

    /**
     * 角色状态（0正常 1停用）
     */
    @ApiModelProperty(value = "角色状态（0正常 1停用）")
    private String status;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 权限菜单集合
     */
    @ApiModelProperty(value = "权限菜单集合")
    List<String> menuList;
}
