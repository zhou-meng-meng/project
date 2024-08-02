package com.example.project.demos.web.dto.list;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.example.project.demos.web.utils.LocalDateConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class MaterialDosageInfo {
    /**
     * 自增主键
     */
    @ApiModelProperty(value = "主键")
    @ExcelIgnore
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 厂区编号
     */
    @ExcelProperty(value = "厂区编号")
    @ApiModelProperty(value = "厂区编号")
    private String factoryCode;
    @ExcelProperty(value = "厂区名称")
    @ApiModelProperty(value = "厂区名称")
    private String factoryName;
    /**
     * 班组日期
     */
    @ExcelProperty(value = "日期",converter = LocalDateConverter.class)
    @ApiModelProperty(value = "班组日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date dutyDate;
    /**
     * 班组 0-白班 1-中班 2-夜班
     */
    @ExcelIgnore
    @ApiModelProperty(value = "班组 0-白班 1-中班 2-夜班")
    private String dutyCode;

    @ExcelProperty(value = "班组")
    @ApiModelProperty(value = "班组")
    private String dutyName;
    /**
     * 机器编号
     */
    @ExcelIgnore
    @ApiModelProperty(value = "机器编号")
    private String machineCode;

    @ExcelProperty(value = "机器号")
    @ApiModelProperty(value = "机器号")
    private String machineName;
    /**
     * 进货单号
     */
    @ExcelProperty(value = "进货单号")
    @ApiModelProperty(value = "进货单号")
    private String stockBillNo;
    /**
     * 单号
     */
    @ExcelProperty(value = "单号")
    @ApiModelProperty(value = "单号")
    private String billNo;
    /**
     * 磨粉棒重量
     */
    @ExcelProperty(value = "磨粉棒重量")
    @ApiModelProperty(value = "磨粉棒重量")
    private BigDecimal grindingWeight;
    /**
     * 机器磅重量
     */
    @ExcelProperty(value = "机器磅重量")
    @ApiModelProperty(value = "机器磅重量")
    private BigDecimal machineWeight;
    /**
     * 差额（磨粉棒重量-机器磅重量）
     */
    @ExcelProperty(value = "差额")
    @ApiModelProperty(value = "差额（磨粉棒重量-机器磅重量）")
    private BigDecimal differentWeight;
    /**
     * 创建者
     */
    @ExcelIgnore
    @ApiModelProperty(value = "创建者英文名")
    private String createBy;

    @ExcelProperty(value = "创建者名字")
    @ApiModelProperty(value = "创建者名字")
    private String createByName;
    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    /**
     * 更新者
     */
    @ExcelIgnore
    @ApiModelProperty(value = "更新者英文名")
    private String updateBy;
    @ExcelProperty(value = "更新者名字")
    @ApiModelProperty(value = "更新者名字")
    private String updateByName;
    /**
     * 更新时间
     */
    @ExcelProperty(value = "更新时间")
    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    @ApiModelProperty(value = "备注")
    private String remark;
}
