package com.example.project.demos.web.dto.sysFactory;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DeleteByIdDTO {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "厂区编号")
    private String code;
    /**
     * 厂区名称
     */
    @ApiModelProperty(value = "厂区名称")
    private String name;
    /**
     * 厂区详细地址
     */
    @ApiModelProperty(value = "厂区详细地址")
    private String address;

    @ApiModelProperty(value = "厂区负责人姓名")
    private String manageName;
    /**
     * 厂区负责人电话
     */
    @ApiModelProperty(value = "厂区负责人电话")
    private String manageTel;
}
