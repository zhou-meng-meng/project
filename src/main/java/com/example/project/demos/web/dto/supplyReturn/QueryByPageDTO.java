package com.example.project.demos.web.dto.supplyReturn;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryByPageDTO {

    /**
     * 物料编号
     */
    @ApiModelProperty(value = "物料编号")
    private String materialCode;
    @ApiModelProperty(value = "物料名称")
    private String materialName;

    @ApiModelProperty(value = "审核状态")
    private String approveState;
    @ApiModelProperty(value = "单据号")
    private String billNo;
    @ApiModelProperty(value = "退回人")
    private String returnUserName;

    @ApiModelProperty(value = "开始日期")
    private String beginDate;
    @ApiModelProperty(value = "结束日期")
    private String endDate;
    @ApiModelProperty(value = "退回入库方编号")
    private String inCode;

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
