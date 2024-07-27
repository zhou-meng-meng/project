package com.example.project.demos.web.dto.approveOperationFlow;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

@Data
public class AddDTO {

    @ApiModelProperty(value = "各审核业务主键")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long businessId;
    /**
     * 业务类型编码
     */
    @ApiModelProperty(value = "业务类型编码")
    private String functionId;

    @ApiModelProperty(value = "提交人")
    private String submitUser;

    @ApiModelProperty(value = "提交时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date submitTime;

    /**
     * 审核状态
     */
    @ApiModelProperty(value = "审核状态")
    private String approveState;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;



}
