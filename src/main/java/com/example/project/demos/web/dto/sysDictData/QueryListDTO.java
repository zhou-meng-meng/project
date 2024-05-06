package com.example.project.demos.web.dto.sysDictData;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryListDTO {

    /**
     * 字典类型
     */
    @ApiModelProperty(value = "字典类型")
    private String dictType;
}
