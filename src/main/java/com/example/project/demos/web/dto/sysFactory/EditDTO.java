package com.example.project.demos.web.dto.sysFactory;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class EditDTO {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 厂区编号
     */
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
    /**
     * 厂区负责人
     */
    @ApiModelProperty(value = "厂区负责人")
    private String manage;
    /**
     * 厂区负责人电话
     */
    @ApiModelProperty(value = "厂区负责人电话")
    private String manageTel;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
}
