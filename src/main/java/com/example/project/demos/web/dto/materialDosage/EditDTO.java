package com.example.project.demos.web.dto.materialDosage;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
     * 厂区编号
     */
    @ApiModelProperty(value = "厂区编号")
    private String factoryCode;
    /**
     * 班组日期
     */
    @ApiModelProperty(value = "班组日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dutyDate;
    /**
     * 班组 0-白班 1-中班 2-夜班
     */
    @ApiModelProperty(value = "班组 0-白班 1-中班 2-夜班")
    private String dutyCode;
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
    private Double grindingWeight;
    /**
     * 机器磅重量
     */
    @ApiModelProperty(value = "机器磅重量")
    private Double machineWeight;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
}
