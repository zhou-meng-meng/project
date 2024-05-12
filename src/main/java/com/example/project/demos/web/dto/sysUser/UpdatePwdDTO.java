package com.example.project.demos.web.dto.sysUser;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdatePwdDTO {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "原密码")
    private String oldPwd;

    @ApiModelProperty(value = "新密码")
    private String newPwd;
}
