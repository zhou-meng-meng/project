package com.example.project.demos.web.dto.materialInfo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DeleteByIdDTO {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 物料编号
     */
    @ApiModelProperty(value = "物料编号")
    private String code;
    /**
     * 物料名称
     */
    @ApiModelProperty(value = "物料名称")
    private String name;

    @ApiModelProperty(value = "型号名称")
    private String modelName;

    @ApiModelProperty(value = "单位名称")
    private String unitName;

    @ApiModelProperty(value = "物料类型名称  0-原材料；1-销售产品")
    private String typeName;

}
