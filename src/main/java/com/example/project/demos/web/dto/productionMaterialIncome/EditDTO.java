package com.example.project.demos.web.dto.productionMaterialIncome;

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
    @ApiModelProperty(value = "自增主键")
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
    /**
     * 生产员工
     */
    @ApiModelProperty(value = "生产员工")
    private String producer;
    @ApiModelProperty(value = "生产员工姓名")
    private String producerName;

    /**
     * 生产日期
     */
    @ApiModelProperty(value = "生产日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date produceTime;
    /**
     * 厂区编号
     */
    @ApiModelProperty(value = "厂区编号")
    private String factoryCode;
    @ApiModelProperty(value = "厂区名称")
    private String factoryName;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
}
