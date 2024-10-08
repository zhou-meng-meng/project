package com.example.project.demos.web.dto.approveOperationQueue;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryByPageDTO {

    @ApiModelProperty(value = "提交开始日期")
    private String beginDate;
    @ApiModelProperty(value = "提交结束日期")
    private String endDate;

    @ApiModelProperty(value = "提交人姓名")
    private String submitUserName;

    @ApiModelProperty(value = "当前审核人")
    private String approveUser;

    @ApiModelProperty(value = "客户名称")
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
