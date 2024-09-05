package com.example.project.demos.web.dto.uploadFileInfo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryFileInfoListDTO {
    @ApiModelProperty(value = "业务主键")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long businessId;

    @ApiModelProperty(value = "页面类型1-往来账列表   null-其他")
    private String pageType;

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
