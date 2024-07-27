package com.example.project.demos.web.dto.materialDosage;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryByPageDTO {
    /**
     * 厂区编号
     */
    @ApiModelProperty(value = "厂区编号")
    private String factoryCode;
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
     * 机器编号
     */
    @ApiModelProperty(value = "机器编号")
    private String machineCode;
    /**
     * 进货单号
     */
    @ApiModelProperty(value = "进货单号")
    private String stockBillNo;
    /**
     * 单号
     */
    @ApiModelProperty(value = "单号")
    private String billNo;

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
