package com.example.project.demos.web.dto.materialDosage;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class QueryByIdOutDTO {

    private static final long serialVersionUID = -89026850526790880L;
    /**
     * 自增主键
     */
    @ApiModelProperty(value = "主键")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 厂区编号
     */
    @ApiModelProperty(value = "厂区编号")
    private String factoryCode;
    @ApiModelProperty(value = "厂区名称")
    private String factoryName;
    /**
     * 班组日期
     */
    @ApiModelProperty(value = "班组日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date dutyDate;
    /**
     * 班组 0-白班 1-中班 2-夜班
     */
    @ApiModelProperty(value = "班组 0-白班 1-中班 2-夜班")
    private String dutyCode;
    @ApiModelProperty(value = "班组")
    private String dutyName;
    /**
     * 机器编号
     */
    @ApiModelProperty(value = "机器编号")
    private String machineCode;
    /**
     * 进货单号
     */
    @ApiModelProperty(value = "进货单号")
    private String stockBillNo;
    /**
     * 单号
     */
    @ApiModelProperty(value = "单号")
    private String billNo;
    /**
     * 磨粉棒重量
     */
    @ApiModelProperty(value = "磨粉棒重量")
    private BigDecimal grindingWeight;
    /**
     * 机器磅重量
     */
    @ApiModelProperty(value = "机器磅重量")
    private BigDecimal machineWeight;
    /**
     * 差额（磨粉棒重量-机器磅重量）
     */
    @ApiModelProperty(value = "差额（磨粉棒重量-机器磅重量）")
    private BigDecimal differentWeight;
    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者英文名")
    private String createBy;
    @ApiModelProperty(value = "创建者名字")
    private String createByName;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;
    /**
     * 更新者
     */
    @ApiModelProperty(value = "更新者英文名")
    private String updateBy;
    @ApiModelProperty(value = "更新者名字")
    private String updateByName;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date updateTime;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

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
