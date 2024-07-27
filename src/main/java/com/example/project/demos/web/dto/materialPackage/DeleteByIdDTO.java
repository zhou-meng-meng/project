package com.example.project.demos.web.dto.materialPackage;

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

    @ApiModelProperty(value = "厂区名称")
    private String factoryName;

    /**
     * 机子号
     */
    @ApiModelProperty(value = "机器号")
    private String machineName;
    /**
     * 日期
     */
    @ApiModelProperty(value = "日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date packageDate;

    @ApiModelProperty(value = "班次名称")
    private String dutyName;

    /**
     * 锅数
     */
    @ApiModelProperty(value = "锅数")
    private BigDecimal potNum;
    /**
     * 合计重量
     */
    @ApiModelProperty(value = "合计重量")
    private BigDecimal tollWeight;
    /**
     * 应出袋数
     */
    @ApiModelProperty(value = "应出袋数")
    private BigDecimal shouldNum;
    /**
     * 实际袋数
     */
    @ApiModelProperty(value = "实际袋数")
    private BigDecimal actualNum;
    /**
     * 差额
     */
    @ApiModelProperty(value = "差额")
    private BigDecimal balanceNum;
}
