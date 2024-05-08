package com.example.project.demos.web.dto.sysUser;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserLoginDTO {
    @ApiModelProperty(value = "用户输入工号/登录名")
    private String userLogin;

    @ApiModelProperty(value = "用户输入密码")
    private String password;
}
