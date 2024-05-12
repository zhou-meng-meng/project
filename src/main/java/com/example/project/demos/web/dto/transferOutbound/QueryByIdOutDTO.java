package com.example.project.demos.web.dto.transferOutbound;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class QueryByIdOutDTO {

    private static final long serialVersionUID = -89026850526790880L;
    /**
     * 自增主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;
    /**
     * 物料编号
     */
    @ApiModelProperty(value = "主键")
    private String materialCode;
    @ApiModelProperty(value = "主键")
    private String materialName;
    /**
     * 调拨数量
     */
    private BigDecimal transferCount;
    /**
     * 调拨类型 0-原材料；1-产量
     */
    @ApiModelProperty(value = "主键")
    private String transferType;
    @ApiModelProperty(value = "主键")
    private String transferTypeName;
    /**
     * 单位
     */
    private String unit;
    /**
     * 单据号
     */
    @ApiModelProperty(value = "主键")
    private String billNo;
    /**
     * 单据(确认)状态
     */
    @ApiModelProperty(value = "主键")
    private String confirmState;
    @ApiModelProperty(value = "主键")
    private String confirmStateName;
    /**
     * 调出厂(仓库)编号
     */
    @ApiModelProperty(value = "主键")
    private String outCode;
    @ApiModelProperty(value = "主键")
    private String outName;
    /**
     * 调入厂（仓库）编号
     */
    @ApiModelProperty(value = "主键")
    private String inCode;
    @ApiModelProperty(value = "主键")
    private String inCodeName;
    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者英文名")
    private String createBy;
    @ApiModelProperty(value = "创建者名字")
    private String createByName;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 更新者
     */
    @ApiModelProperty(value = "更新者英文名")
    private String updateBy;
    @ApiModelProperty(value = "更新者名字")
    private String updateByName;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
