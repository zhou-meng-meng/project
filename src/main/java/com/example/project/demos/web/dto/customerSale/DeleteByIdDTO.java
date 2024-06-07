package com.example.project.demos.web.dto.customerSale;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DeleteByIdDTO {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "客户编号")
    private String code;
    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    private String name;

    /**
     * 联系人
     */
    @ApiModelProperty(value = "联系人")
    private String linkUser;
    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    private String linkPhoneNo;

    @ApiModelProperty(value = "客户地址")
    private String address;

    /**
     * 所属销售
     */
    @ApiModelProperty(value = "所属销售")
    private String salerName;
}
