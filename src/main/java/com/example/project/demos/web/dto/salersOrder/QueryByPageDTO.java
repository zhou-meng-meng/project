package com.example.project.demos.web.dto.salersOrder;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryByPageDTO {


    @ApiModelProperty(value = "物料名称")
    private String materialName;
    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ApiModelProperty(value = "单据号")
    private String billCode;
    @ApiModelProperty(value = "销售员英文名")
    private String saler;

    @ApiModelProperty(value = "销售员姓名")
    private String salerName;


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
