package com.example.project.demos.web.dto.salesReturn;

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

    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ApiModelProperty(value = "物料编号")
    private String materialCode;
    @ApiModelProperty(value = "物料名称")
    private String materialName;
    @ApiModelProperty(value = "退回数量")
    private BigDecimal returnCount;
    @ApiModelProperty(value = "退回方名称")
    private String inName;
    @ApiModelProperty(value = "退回人姓名")
    private String returnUserName;

    /**
     * 退货时间
     */
    @ApiModelProperty(value = "退货时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date returnTime;
}
