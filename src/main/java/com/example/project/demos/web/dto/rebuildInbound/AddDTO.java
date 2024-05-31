package com.example.project.demos.web.dto.rebuildInbound;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AddDTO {

    /**
     * 物料编号
     */
    @ApiModelProperty(value = "物料编号")
    private String materialCode;
    @ApiModelProperty(value = "物料名称")
    private String materialName;

    /**
     * 重造出库数量
     */
    @ApiModelProperty(value = "重造出库数量")
    private BigDecimal rebuildCount;
    /**
     * 重造时间
     */
    @ApiModelProperty(value = "重造时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date rebuildDate;
    @ApiModelProperty(value = "入库方编号")
    private String inCode;
    @ApiModelProperty(value = "入库方名称")
    private String inName;
    @ApiModelProperty(value = "班组编号")
    private String dutyCode;
    @ApiModelProperty(value = "班组名称")
    private String dutyName;
    @ApiModelProperty(value = "机器号")
    private String machineCode;
    @ApiModelProperty(value = "机器号")
    private String machineName;


    /**
     * 单据号
     */
    @ApiModelProperty(value = "单据号")
    private String billNo;
    /**
     * 单据状态
     */
    @ApiModelProperty(value = "单据状态")
    private String billState;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
}
