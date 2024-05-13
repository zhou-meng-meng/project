package com.example.project.demos.web.dto.materialInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class EditDTO {
    /**
     * 自增主键
     */
    @ApiModelProperty(value = "主键")
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

    /**
     * 单位
     */
    @ApiModelProperty(value = "单位")
    private String unit;

    /**
     * 物料类型  0-原材料；1-销售产品
     */
    @ApiModelProperty(value = "物料类型  0-原材料；1-销售产品")
    private String type;

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
