package com.example.project.demos.web.dto.customerPayDetail;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

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
