package com.example.project.demos.web.dto.transferOutbound;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
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
     * 单据(确认)状态
     */
    @ApiModelProperty(value = "单据(确认)状态 0-未确认 ;1-已确认；2-拒绝确认")
    private String confirmState;


    /**
     * 单据(确认)状态时间
     */
    @ApiModelProperty(value = "单据(确认)状态时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date confirmTime;

    /**
     * 单据(确认)理由
     */
    @ApiModelProperty(value = "单据(确认)理由")
    private String confirmOpinion;

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
