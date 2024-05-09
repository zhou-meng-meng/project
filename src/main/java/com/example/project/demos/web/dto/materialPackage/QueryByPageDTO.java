package com.example.project.demos.web.dto.materialPackage;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class QueryByPageDTO {

    /**
     * 厂区编码
     */
    @ApiModelProperty(value = "厂区编码")
    private String factoryCode;

    /**
     * 机子号
     */
    @ApiModelProperty(value = "机子号")
    private String machineCode;
    /**
     * 班组日期
     */
    @ApiModelProperty(value = "开始日期")
    private String beginDate;

    @ApiModelProperty(value = "结束日期")
    private String endDate;
    /**
     * 班组 0-白班 1-中班 2-夜班
     */
    @ApiModelProperty(value = "班组 0-白班  1-夜班")
    private String dutyCode;

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
