package com.example.project.demos.web.dto.sysFactory;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryPopPageListDTO {


    @ApiModelProperty(value = "编号")
    private String code;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "类型F-厂区；S-仓库")
    private String type;

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
