package com.example.project.demos.web.dto.customerSupply;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DeleteByIdDTO {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String code;
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
    /**
     * 客户地址
     */
    @ApiModelProperty(value = "客户地址")
    private String address;
    @ApiModelProperty(value = "传真")
    private String faxNo;
}
