package com.example.project.demos.web.dto.transferOutbound;

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

    /**
     * 调拨数量
     */
    private BigDecimal transferCount;
    /**
     * 调拨类型 0-原材料；1-产量
     */
    @ApiModelProperty(value = "调拨类型 0-原材料；1-产量")
    private String transferType;


    @ApiModelProperty(value = "调拨日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date transferDate;
    /**
     * 单位
     */
    @ApiModelProperty(value = "单位")
    private String unit;
    /**
     * 单据号
     */
    @ApiModelProperty(value = "单据号")
    private String billNo;

    /**
     * 调出厂(仓库)编号
     */
    @ApiModelProperty(value = "调出厂(仓库)编号")
    private String outCode;

    /**
     * 调入厂（仓库）编号
     */
    @ApiModelProperty(value = "调入厂（仓库）编号")
    private String inCode;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
}
