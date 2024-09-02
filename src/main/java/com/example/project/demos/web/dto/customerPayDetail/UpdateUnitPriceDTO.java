package com.example.project.demos.web.dto.customerPayDetail;

import com.example.project.demos.web.dto.list.UploadFileId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class UpdateUnitPriceDTO {
    /**
     * 自增主键
     */
    @ApiModelProperty(value = "主键")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long businessId;


    /**
     * 修改后单价
     */
    @ApiModelProperty(value = "修改后单价")
    private BigDecimal unitPrice;

    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    private BigDecimal count;

    /**
     * 修改前总金额
     */
    @ApiModelProperty(value = "修改前总金额")
    private BigDecimal preTollAmount;

    /**
     * 修改后总金额
     */
    @ApiModelProperty(value = "修改后总金额")
    private BigDecimal tollAmount;

    @ApiModelProperty(value = "菜单码")
    private String functionId;

    @ApiModelProperty(value = "备注")
    private String remark;

}
