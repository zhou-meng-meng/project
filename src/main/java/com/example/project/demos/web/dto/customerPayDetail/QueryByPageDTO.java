package com.example.project.demos.web.dto.customerPayDetail;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryByPageDTO {

    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号",required = true)
    private String customerCode;

    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称",required = true)
    private String customerName;

    @ApiModelProperty(value = "物料名称")
    private String materialName;

    @ApiModelProperty(value = "物料开始日期")
    private String beginDate;

    @ApiModelProperty(value = "物料结束日期")
    private String endDate;

    @ApiModelProperty(value = "打款开始日期")
    private String payStartDate;

    @ApiModelProperty(value = "打款结束日期")
    private String payEndDate;

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
