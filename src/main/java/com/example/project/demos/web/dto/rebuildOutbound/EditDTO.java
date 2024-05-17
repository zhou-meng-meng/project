package com.example.project.demos.web.dto.rebuildOutbound;

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

    /**
     * 重造出库数量
     */
    @ApiModelProperty(value = "重造出库数量")
    private BigDecimal rebuildCount;
    /**
     * 重造时间
     */
    @ApiModelProperty(value = "重造时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date rebuildDate;
    /**
     * 入库厂区编号
     */
    @ApiModelProperty(value = "入库厂区编号")
    private String factoryCode;

    /**
     * 单据号
     */
    @ApiModelProperty(value = "单据号")
    private String billNo;
    /**
     * 单据状态
     */
    @ApiModelProperty(value = "单据状态")
    private String billState;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
}
