package com.example.project.demos.web.dto.customerSupply;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryByPageDTO {

    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    private String code;
    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    private String name;

    /**
     * 联系人
     */
    @ApiModelProperty(value = "联系人")
    private String linkUser;

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
