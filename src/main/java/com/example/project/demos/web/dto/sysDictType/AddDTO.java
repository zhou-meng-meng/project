package com.example.project.demos.web.dto.sysDictType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AddDTO {
    /**
     * 字典名称
     */
    @ApiModelProperty(value = "字典名称")
    private String dictName;
    /**
     * 字典类型
     */
    @ApiModelProperty(value = "字典类型")
    private String dictType;
}
