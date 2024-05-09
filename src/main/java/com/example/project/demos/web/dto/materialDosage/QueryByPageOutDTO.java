package com.example.project.demos.web.dto.materialDosage;

import com.example.project.demos.web.dto.list.MaterialDosageInfo;
import com.example.project.demos.web.dto.list.SysFactoryInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class QueryByPageOutDTO {
    /**
     * 总数:200
     */
    @ApiModelProperty(value = "总数 ")
    private Integer turnPageTotalNum;

    private List<MaterialDosageInfo> materialDosageInfoList;

    /**
     * 磨粉棒重量合计
     */
    @ApiModelProperty(value = "磨粉棒重量合计")
    private Double grindingWeightToll;
    /**
     * 机器磅重量合计
     */
    @ApiModelProperty(value = "机器磅重量合计")
    private Double machineWeightToll;
    /**
     * 差额（磨粉棒重量-机器磅重量）合计
     */
    @ApiModelProperty(value = "差额合计")
    private Double differentWeightToll;

    /**
     * 操作结果编码:null
     */
    @ApiModelProperty(value = "操作结果编码")
    private String errorCode;

    /**
     * 操作结果信息:null
     */
    @ApiModelProperty(value = "操作结果信息")
    private String errorMsg;

}
