package com.example.project.demos.web.dto.materialInventory;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryByPagePopDTO {

    /**
     * 物料编号
     */
    @ApiModelProperty(value = "物料编号")
    private String materialCode;
    @ApiModelProperty(value = "物料名称")
    private String materialName;

    @ApiModelProperty(value = "厂区/仓库编号")
    private String stockCode;

    @ApiModelProperty(value = "物料类型：0-原材料；1-产量；2-不合格")
    private String materialType;

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
