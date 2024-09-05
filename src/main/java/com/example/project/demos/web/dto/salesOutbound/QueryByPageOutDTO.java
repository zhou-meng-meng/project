package com.example.project.demos.web.dto.salesOutbound;

import com.example.project.demos.web.dto.list.SalesOutboundInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class QueryByPageOutDTO {
    /**
     * 总数:200
     */
    @ApiModelProperty(value = "总数 ")
    private Integer turnPageTotalNum;

    @ApiModelProperty(value = "合计数量")
    private BigDecimal sumCount;
    @ApiModelProperty(value = "合计金额")
    private BigDecimal sumAmt;

    private List<SalesOutboundInfo> salesOutboundInfoList;

    /**
     * 操作结果编码:null
     */
    @ApiModelProperty(value = "操作结果编码")
    private String errorCode;

    /**
     * 操作结果信息:null
     */
    @ApiModelProperty(value = "操作结果信息")
    private String errorMsg;

}
