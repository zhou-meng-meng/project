package com.example.project.demos.web.dto.sysStorehouse;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DeleteByIdDTO {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "仓库编号")
    private String code;
    /**
     * 仓库名称
     */
    @ApiModelProperty(value = "仓库名称")
    private String name;
    /**
     * 仓库详细地址
     */
    @ApiModelProperty(value = "仓库详细地址")
    private String address;

    @ApiModelProperty(value = "仓库负责人姓名")
    private String manageName;

    /**
     * 仓库负责人电话
     */
    @ApiModelProperty(value = "仓库负责人电话")
    private String manageTel;
}
