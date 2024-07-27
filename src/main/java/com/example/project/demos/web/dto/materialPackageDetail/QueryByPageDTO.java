package com.example.project.demos.web.dto.materialPackageDetail;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class QueryByPageDTO {

    /**
     * 装袋表主键
     */
    @ApiModelProperty(value = "装袋表主键")
    private Long packageId;

}
