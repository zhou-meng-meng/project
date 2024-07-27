package com.example.project.demos.web.dto.productionMaterialIncome;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryByPageDTO {

    /**
     * 物料编号
     */
    @ApiModelProperty(value = "物料编号")
    private String materialCode;
    @ApiModelProperty(value = "物料名称")
    private String materialName;

    @ApiModelProperty(value = "生产员工")
    private String producerName;

    @ApiModelProperty(value = "开始日期")
    private String beginDate;

    @ApiModelProperty(value = "结束")
    private String endDate;

    @ApiModelProperty(value = "入库方编码")
    private String inCode;

    @ApiModelProperty(value = "班组编号")
    private String dutyCode;

    @ApiModelProperty(value = "机器编号")
    private String machineCode;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

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
