package com.example.project.demos.web.dto.salersOrderReturn;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryByPageDTO {

    @ApiModelProperty(value = "物料编号")
    private String materialCode;
    @ApiModelProperty(value = "物料名称")
    private String materialName;
    @ApiModelProperty(value = "退货方名称")
    private String customerName;

    @ApiModelProperty(value = "单据号")
    private String billNo;
    @ApiModelProperty(value = "退回方编号")
    private String inCode;
    @ApiModelProperty(value = "退回人")
    private String returnUser;
    @ApiModelProperty(value = "退回人姓名")
    private String returnUserName;


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
