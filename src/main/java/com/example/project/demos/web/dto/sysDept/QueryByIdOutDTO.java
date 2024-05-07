package com.example.project.demos.web.dto.sysDept;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class QueryByIdOutDTO {

    private static final long serialVersionUID = -89026850526790880L;
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;
    /**
     * 厂区编号
     */
    @ApiModelProperty(value = "厂区编号")
    private String code;
    /**
     * 厂区名称
     */
    @ApiModelProperty(value = "厂区名称")
    private String name;
    /**
     * 厂区详细地址
     */
    @ApiModelProperty(value = "厂区详细地址")
    private String address;
    /**
     * 厂区负责人
     */
    @ApiModelProperty(value = "厂区负责人")
    private String manage;

    @ApiModelProperty(value = "厂区负责人姓名")
    private String manageName;
    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    private String createBy;
    @ApiModelProperty(value = "创建人名称")
    private String createName;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**
     * 更新者
     */
    @ApiModelProperty(value = "修改人")
    private String updateBy;
    @ApiModelProperty(value = "修改人名称")
    private String updateName;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
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
