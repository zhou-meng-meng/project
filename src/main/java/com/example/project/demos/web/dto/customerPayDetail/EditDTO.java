package com.example.project.demos.web.dto.customerPayDetail;

import com.example.project.demos.web.dto.list.UploadFileId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class EditDTO {
    /**
     * 自增主键
     */
    @ApiModelProperty(value = "自增主键")
    @JsonSerialize(using = ToStringSerializer.class)
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

    @ApiModelProperty(value = "打款日期")
    private Date payDate;

    @ApiModelProperty(value = "是否默认值 Y-是；N-否  前端固定值N")
    private String isDefault;

    @ApiModelProperty(value = "经办人英文名")
    private String operatorBy;
    @ApiModelProperty(value = "经办人名字")
    private String operatorByName;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "附件主键集合")
    private List<UploadFileId> fileIdList;
}
