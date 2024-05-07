package com.example.project.demos.web.dto.sysUser;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AddOutDTO {
    /**
     * 操作结果编码:null
     */
    @ApiModelProperty(value = "操作结果编码")
    private String errorCode;

    /**
     * 操作结果信息:null
     */
    @ApiModelProperty(value = "操作结果信息")
    private String errorMsg;
}
