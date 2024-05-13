package com.example.project.demos.web.dto.sysMenu;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryMenuTreeDTO {

    /**
     * 角色ID
     */
    @ApiModelProperty(value = "角色ID")
    private String roleId;

}
