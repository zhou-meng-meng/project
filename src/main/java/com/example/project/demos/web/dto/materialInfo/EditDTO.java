package com.example.project.demos.web.dto.materialInfo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class EditDTO {
    /**
     * 自增主键
     */
    @ApiModelProperty(value = "主键")
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
    /**
     * 型号
     */
    @ApiModelProperty(value = "型号编号")
    private String model;
    @ApiModelProperty(value = "型号名称")
    private String modelName;

    /**
     * 单位
     */
    @ApiModelProperty(value = "单位")
    private String unit;
    @ApiModelProperty(value = "单位名称")
    private String unitName;

    /**
     * 物料类型  0-原材料；1-销售产品
     */
    @ApiModelProperty(value = "物料类型  0-原材料；1-销售产品")
    private String type;
    @ApiModelProperty(value = "物料类型名称  0-原材料；1-销售产品")
    private String typeName;

    /**
     * 供应商编号（来料使用）
     */
    @ApiModelProperty(value = "供应商编号（来料使用）")
    private String supplyerCode;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
}
