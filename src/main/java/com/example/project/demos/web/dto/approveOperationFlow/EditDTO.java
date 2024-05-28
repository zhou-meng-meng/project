package com.example.project.demos.web.dto.approveOperationFlow;

import com.example.project.demos.web.entity.CustomerAccountRelEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
}
