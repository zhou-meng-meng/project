package com.example.project.demos.web.dto.transferOutbound;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class DeleteByIdDTO {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 物料编号
     */
    @ApiModelProperty(value = "物料编号")
    private String materialCode;
    @ApiModelProperty(value = "物料名称")
    private String materialName;

    /**
     * 调拨数量
     */
    private BigDecimal transferCount;

    /**
     * 单据号
     */
    @ApiModelProperty(value = "单据号")
    private String billNo;

    @ApiModelProperty(value = "调出厂(仓库)名称")
    private String outName;

    @ApiModelProperty(value = "调入厂（仓库）名称")
    private String inName;
}
