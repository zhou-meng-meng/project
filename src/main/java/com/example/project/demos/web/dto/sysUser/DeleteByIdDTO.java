package com.example.project.demos.web.dto.sysUser;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DeleteByIdDTO {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 用户工号
     */
    @ApiModelProperty(value = "用户工号")
    private String userId;
    /**
     * 用户英文登录名
     */
    @ApiModelProperty(value = "用户英文登录名")
    private String userLogin;
    /**
     * 用户姓名
     */
    @ApiModelProperty(value = "用户姓名")
    private String userName;

    @ApiModelProperty(value = "用户类型（（0-总公司;1-厂区;2-仓库））")
    private String userTypeName;


    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "部门名称")
    private String deptName;
}
