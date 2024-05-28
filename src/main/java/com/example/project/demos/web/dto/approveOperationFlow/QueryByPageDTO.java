package com.example.project.demos.web.dto.approveOperationFlow;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class QueryByPageDTO {

    @ApiModelProperty(value = "提交人姓名")
    private String submitUserName;

    /**
     * 审核开始日期
     */
    @ApiModelProperty(value = "审核开始日期")
    private String beginDate;
    @ApiModelProperty(value = "审核结束日期")
    private String endDate;
    /**
     * 审核状态
     */
    @ApiModelProperty(value = "审核状态")
    private String approveState;

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
