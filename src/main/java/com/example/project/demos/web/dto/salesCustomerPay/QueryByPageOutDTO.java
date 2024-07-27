package com.example.project.demos.web.dto.salesCustomerPay;

import com.example.project.demos.web.dto.list.SalesCustomerPayInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;

@Data
public class QueryByPageOutDTO {
    /**
     * 总数:200
     */
    @ApiModelProperty(value = "总数 ")
    private Integer turnPageTotalNum;

    private List<SalesCustomerPayInfo> salesCustomerPayInfoList;

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
