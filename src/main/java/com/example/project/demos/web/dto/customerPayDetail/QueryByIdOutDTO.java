package com.example.project.demos.web.dto.customerPayDetail;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class QueryByIdOutDTO {

    private static final long serialVersionUID = -89026850526790880L;
    /**
     * 自增主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "自增主键")
    private Long id;
    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    private String customerCode;
    @ApiModelProperty(value = "客户名称")
    private String customerName;

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

    @ApiModelProperty(value = "折扣金额")
    private BigDecimal discountBalance;
    /**
     * 付款类型  0-入款 1-出款
     */
    @ApiModelProperty(value = "付款类型  0-入款 1-出款")
    private String payType;
    @ApiModelProperty(value = "付款类型")
    private String payTypeName;
    @ApiModelProperty(value = "经办人英文名")
    private String operatorBy;
    @ApiModelProperty(value = "经办人名字")
    private String operatorByName;
    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者英文名")
    private String createBy;
    @ApiModelProperty(value = "创建者名字")
    private String createByName;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    /**
     * 更新者
     */
    @ApiModelProperty(value = "更新者英文名")
    private String updateBy;
    @ApiModelProperty(value = "更新者名字")
    private String updateByName;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 操作结果编码:null
     */
    @ApiModelProperty(value = "操作结果编码")
    private String errorCode;

    /**
     * 操作结果信息:null
     */
    @ApiModelProperty(value = "操作结果信息")
    private String errorMsg;
}
