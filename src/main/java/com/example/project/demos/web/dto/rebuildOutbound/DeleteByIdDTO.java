package com.example.project.demos.web.dto.rebuildOutbound;

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
    @ApiModelProperty(value = "物料编号")
    private String materialCode;
    @ApiModelProperty(value = "物料名称")
    private String materialName;
    /**
     * 重造出库数量
     */
    @ApiModelProperty(value = "重造出库数量")
    private BigDecimal rebuildCount;
    /**
     * 重造时间
     */
    @ApiModelProperty(value = "重造日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date rebuildDate;


    @ApiModelProperty(value = "出库方名称")
    private String outName;

    @ApiModelProperty(value = "班组名称")
    private String dutyName;
    @ApiModelProperty(value = "机器号")
    private String machineName;

}
