package com.example.project.demos.web.dto.uploadFileInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryFileInfoEditListDTO {
    @ApiModelProperty(value = "业务主键")
    private Long businessId;

}
