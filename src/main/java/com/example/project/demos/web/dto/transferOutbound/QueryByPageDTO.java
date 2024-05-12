package com.example.project.demos.web.dto.transferOutbound;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class QueryByPageDTO {

    /**
     * 物料编号
     */
    @ApiModelProperty(value = "物料编号")
    private String materialCode;
    @ApiModelProperty(value = "物料名称")
    private String materialName;

    /**
     * 调拨类型 0-原材料；1-产量
     */
    @ApiModelProperty(value = "调拨类型 0-原材料；1-产量")
    private String transferType;


    @ApiModelProperty(value = "调拨日期")
    private String beginDate;
    @ApiModelProperty(value = "调拨日期")
    private String endDate;

    /**
     * 单据(确认)状态
     */
    @ApiModelProperty(value = "单据(确认)状态 0-未确认 ;1-已确认；2-拒绝确认")
    private String confirmState;

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
