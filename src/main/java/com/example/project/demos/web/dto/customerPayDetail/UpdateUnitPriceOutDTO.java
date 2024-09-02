package com.example.project.demos.web.dto.customerPayDetail;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdateUnitPriceOutDTO {
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
