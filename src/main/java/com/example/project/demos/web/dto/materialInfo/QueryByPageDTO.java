package com.example.project.demos.web.dto.materialInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class QueryByPageDTO {

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
     * 物料类型  0-原材料；1-销售产品
     */
    @ApiModelProperty(value = "物料类型  0-原材料；1-销售产品")
    private String type;

    /**
     * 供应商编号（来料使用）
     */
    @ApiModelProperty(value = "供应商名称")
    private String supplyerName;


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
