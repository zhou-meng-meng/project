package com.example.project.demos.web.dto.sysFactory;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryListDTO {

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
     * 翻页数据起始位置:1
     */
    @ApiModelProperty(value = "翻页数据起始位置",required=true)
    private Integer turnPageBeginPos;

    /**
     * 翻页一次显示数量:20
     */
    @ApiModelProperty(value = "翻页一次显示数量",required=true)
    private Integer turnPageShowNum;
}
