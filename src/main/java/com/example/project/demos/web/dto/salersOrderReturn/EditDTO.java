package com.example.project.demos.web.dto.salersOrderReturn;

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
     * 出库数量
     */
    @ApiModelProperty(value = "退货数量")
    private BigDecimal returnCount;
    /**
     * 单价
     */
    @ApiModelProperty(value = "单价")
    private BigDecimal unitPrice;
    /**
     * 总金额
     */
    @ApiModelProperty(value = "总金额")
    private BigDecimal tollAmount;
    @ApiModelProperty(value = "退货客户编号")
    private String customerCode;
    @ApiModelProperty(value = "退货客户名称")
    private String customerName;

    @ApiModelProperty(value = "退回人英文名")
    private String returnUser;
    @ApiModelProperty(value = "退回人姓名")
    private String returnUserName;

    /**
     * 销售时间
     */
    @ApiModelProperty(value = "退货日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date returnTime;

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
