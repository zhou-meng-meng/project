package com.example.project.demos.web.dto.approveOperationQueue;

import com.example.project.demos.web.entity.CustomerAccountRelEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AddDTO {

    /**
     * 审核流水id
     */
    @ApiModelProperty(value = "审核流水id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long operationFlowId;
    /**
     * 各审核业务主键
     */
    @ApiModelProperty(value = "各审核业务主键")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long businessId;
    /**
     * 审核人英文名
     */
    @ApiModelProperty(value = "审核人英文名")
    private String approveUser;

    /**
     * 业务类型编码
     */
    @ApiModelProperty(value = "业务类型编码")
    private String functionId;

    /**
     * 提交人
     */
    @ApiModelProperty(value = "提交人")
    private String submitUser;

    /**
     * 提交时间
     */
    @ApiModelProperty(value = "提交时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date submitTime;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

}
