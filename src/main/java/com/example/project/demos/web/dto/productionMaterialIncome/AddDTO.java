package com.example.project.demos.web.dto.productionMaterialIncome;

import com.example.project.demos.web.dto.list.ProductProducerInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
     * 入库数量
     */
    @ApiModelProperty(value = "入库数量")
    private BigDecimal incomeNum;

    /**
     * 生产者及产量集合
     */
    @ApiModelProperty(value = "生产者及产量集合")
    List<ProductProducerInfo> producerInfoList;

    /**
     * 生产日期
     */
    @ApiModelProperty(value = "生产日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date produceTime;
    /**
     * 厂区编号
     */
    @ApiModelProperty(value = "厂区编号")
    private String inCode;
    @ApiModelProperty(value = "厂区名称")
    private String inName;
    @ApiModelProperty(value = "班组编号")
    private String dutyCode;
    @ApiModelProperty(value = "班组名称")
    private String dutyName;
    @ApiModelProperty(value = "机器编号")
    private String machineCode;
    @ApiModelProperty(value = "机器名称")
    private String machineName;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
}
