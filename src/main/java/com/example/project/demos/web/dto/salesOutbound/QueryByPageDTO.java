package com.example.project.demos.web.dto.salesOutbound;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryByPageDTO {

    @ApiModelProperty(value = "物料编号")
    private String materialCode;
    @ApiModelProperty(value = "物料名称")
    private String materialName;
    @ApiModelProperty(value = "购货方名称")
    private String customerName;

    @ApiModelProperty(value = "单据号")
    private String billNo;
    @ApiModelProperty(value = "出库方编号")
    private String outCode;

    @ApiModelProperty(value = "销售员登录名")
    private String saler;
    @ApiModelProperty(value = "销售员姓名")
    private String salerName;

    @ApiModelProperty(value = "开始日期")
    private String beginDate;

    @ApiModelProperty(value = "结束日期")
    private String endDate;


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
