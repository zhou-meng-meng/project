package com.example.project.demos.web.dto.materialPackageDetail;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class QueryByPageDTO {

    /**
     * 装袋表主键
     */
    @ApiModelProperty(value = "装袋表主键")
    private Long packageId;

}
