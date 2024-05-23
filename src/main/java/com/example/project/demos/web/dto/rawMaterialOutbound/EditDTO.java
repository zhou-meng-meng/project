package com.example.project.demos.web.dto.rawMaterialOutbound;

import com.baomidou.mybatisplus.annotation.TableId;
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
     * 单价
     */
    @ApiModelProperty(value = "单价")
    private BigDecimal unitPrice;
    /**
     * 出库数量
     */
    @ApiModelProperty(value = "出库数量")
    private BigDecimal count;
    /**
     * 总金额
     */
    @ApiModelProperty(value = "总金额")
    private BigDecimal tollAmount;
    /**
     * 出库方编号
     */
    @ApiModelProperty(value = "出库方编号")
    private String outCode;

    /**
     * 领用人
     */
    @ApiModelProperty(value = "领用人")
    private String receiver;

    /**
     * 领用时间
     */
    @ApiModelProperty(value = "领用时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date receiveTime;
    /**
     * 单据号
     */
    @ApiModelProperty(value = "单据号")
    private String billNo;
    /**
     * 审批状态
     */
    @ApiModelProperty(value = "审批状态")
    private String approveState;
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
