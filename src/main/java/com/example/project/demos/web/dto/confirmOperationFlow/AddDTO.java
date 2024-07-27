package com.example.project.demos.web.dto.confirmOperationFlow;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

@Data
public class AddDTO {

    /**
     * 自增主键
     */
    @ApiModelProperty(value = "自增主键")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @ApiModelProperty(value = "各审核业务主键")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long businessId;
    /**
     * 业务类型编码
     */
    @ApiModelProperty(value = "业务类型编码")
    private String functionId;

    /**
     * 审核流水主键
     */
    @ApiModelProperty(value = "审核流水主键")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long approveOperationId;
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

    @ApiModelProperty(value = "审核时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date approveTime;
    /**
     * 审核状态
     */
    @ApiModelProperty(value = "审核状态")
    private String approveState;

    /**
     * 审核意见
     */
    @ApiModelProperty(value = "审核意见")
    private String approveOpinion;
    @ApiModelProperty(value = "确认状态")
    private String confirmState;
    @ApiModelProperty(value = "确认状态")
    private String confirmStateName;
    @ApiModelProperty(value = "确认意见")
    private String confirmOpinion;
    /**
     * 确认人英文名
     */
    @ApiModelProperty(value = "确认人英文名")
    private String confirmUser;

    /**
     * 确认时间
     */
    @ApiModelProperty(value = "确认时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date confirmTime;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

}
