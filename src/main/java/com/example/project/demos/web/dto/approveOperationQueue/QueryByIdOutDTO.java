package com.example.project.demos.web.dto.approveOperationQueue;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

@Data
public class QueryByIdOutDTO {

    private static final long serialVersionUID = -89026850526790880L;
    /**
     * 自增主键
     */
    @ApiModelProperty(value = "自增主键")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
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
    @ApiModelProperty(value = "审核人姓名")
    private String approveUserName;
    /**
     * 业务类型编码
     */
    @ApiModelProperty(value = "业务类型编码")
    private String functionId;
    @ApiModelProperty(value = "业务类型名称")
    private String functionName;
    /**
     * 提交人
     */
    @ApiModelProperty(value = "提交人")
    private String submitUser;
    @ApiModelProperty(value = "提交人姓名")
    private String submitUserName;
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
