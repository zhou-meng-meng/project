package com.example.project.demos.web.dto.confirmOperationQueue;

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
     * 确认流水id
     */
    @ApiModelProperty(value = "自增主键")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long confirmFlowId;
    /**
     * 各确认业务主键
     */
    @ApiModelProperty(value = "自增主键")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long businessId;
    /**
     * 确认人英文名
     */
    @ApiModelProperty(value = "确认人英文名")
    private String confirmUser;

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
     * 审核人
     */
    @ApiModelProperty(value = "审核人英文名")
    private String approveUser;

    /**
     * 审核时间
     */
    @ApiModelProperty(value = "审核时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date approveTime;

}
