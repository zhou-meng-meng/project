package com.example.project.demos.web.dto.productionMaterialIncome;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

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
     * 入库数量
     */
    @ApiModelProperty(value = "入库数量")
    private BigDecimal incomeNum;
    @ApiModelProperty(value = "班组编号")
    private String dutyCode;
    @ApiModelProperty(value = "班组名称")
    private String dutyName;
    @ApiModelProperty(value = "机器编号")
    private String machineCode;
    @ApiModelProperty(value = "机器名称")
    private String machineName;
    /**
     * 生产员工
     */
    @ApiModelProperty(value = "生产员工姓名")
    private String producerName;

    @ApiModelProperty(value = "厂区名称")
    private String inName;
}
