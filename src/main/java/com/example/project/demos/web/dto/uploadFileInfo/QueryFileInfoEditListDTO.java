package com.example.project.demos.web.dto.uploadFileInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryFileInfoEditListDTO {
    @ApiModelProperty(value = "主键")
    private Long id;
    @ApiModelProperty(value = "页面类型1-往来账列表   null-其他")
    private String pageType;

}
