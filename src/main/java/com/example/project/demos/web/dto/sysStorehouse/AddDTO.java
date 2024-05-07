package com.example.project.demos.web.dto.sysStorehouse;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AddDTO {

    /**
     * 仓库编号
     */
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
    /**
     * 仓库负责人
     */
    @ApiModelProperty(value = "仓库负责人英文名")
    private String manage;

    /**
     * 仓库负责人电话
     */
    @ApiModelProperty(value = "仓库负责人电话")
    private String manageTel;

    /**
     * 所属仓库编号
     */
    @ApiModelProperty(value = "所属厂区编号")
    private String factoryCode;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
}
