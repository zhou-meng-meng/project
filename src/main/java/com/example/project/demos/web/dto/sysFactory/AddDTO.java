package com.example.project.demos.web.dto.sysFactory;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AddDTO {

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
    @ApiModelProperty(value = "厂区负责人英文名")
    private String manage;
    @ApiModelProperty(value = "厂区负责人姓名")
    private String manageName;
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
