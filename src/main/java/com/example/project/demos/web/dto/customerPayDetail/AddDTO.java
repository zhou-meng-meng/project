package com.example.project.demos.web.dto.customerPayDetail;

import com.example.project.demos.web.entity.CustomerAccountRelEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class AddDTO {

    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    private String customerCode;

    /**
     * 物料总金额
     */
    @ApiModelProperty(value = "物料总金额")
    private BigDecimal materialBalance;

    /**
     * 账面余额
     */
    @ApiModelProperty(value = "账面余额")
    private BigDecimal bookBalance;
    /**
     * 打款金额
     */
    @ApiModelProperty(value = "打款金额")
    private BigDecimal payBalance;
    /**
     * 退回金额
     */
    @ApiModelProperty(value = "退回金额")
    private BigDecimal returnBalance;
    /**
     * 付款类型  0-入款 1-出款
     */
    @ApiModelProperty(value = "付款类型  0-入款 1-出款")
    private String payType;

    /**
     *
     */
    @ApiModelProperty(value = "创建人")
    private String createBy;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

}
