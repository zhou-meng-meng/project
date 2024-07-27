package com.example.project.demos.web.dto.sysLog;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class QueryByPageDTO {
    /**
     * 操作人
     */
    private String userName;
    /**
     * 操作时间
     */
    private String beginDate;
    private String endDate;
    /**
     * 操作内容说明
     */
    private String operationInfo;
    /**
     * 操作结果 S-成功; F-失败
     */
    private String operationResult;

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
