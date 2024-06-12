package com.example.project.demos.web.dto.sysDictData;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DeleteByIdDTO {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @ApiModelProperty(value = "字典类型")
    private String dictType;

    @ApiModelProperty(value = "字典编码")
    private String dictCode;

    @ApiModelProperty(value = "字典值")
    private String dictValue;
}
