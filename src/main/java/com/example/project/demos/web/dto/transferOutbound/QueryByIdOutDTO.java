package com.example.project.demos.web.dto.transferOutbound;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 物料编号
     */
    @ApiModelProperty(value = "物料编号")
    private String materialCode;
    @ApiModelProperty(value = "物料名称")
    private String materialName;

    @ApiModelProperty(value = "型号")
    private String model;
    @ApiModelProperty(value = "物料名称")
    private String modelName;

    @ApiModelProperty(value = "单位")
    private String unit;
    @ApiModelProperty(value = "单位名称")
    private String unitName;
    /**
     * 调拨数量
     */
    private BigDecimal transferCount;

    @ApiModelProperty(value = "调拨日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date transferDate;

    /**
     * 调拨类型 0-原材料；1-产量
     */
    @ApiModelProperty(value = "调拨类型 0-原材料；1-产量")
    private String transferType;
    @ApiModelProperty(value = "调拨类型 0-原材料；1-产量")
    private String transferTypeName;

    /**
     * 单据号
     */
    @ApiModelProperty(value = "单据号")
    private String billNo;
    @ApiModelProperty(value = "确认人英文名")
    private String confirmUser;
    @ApiModelProperty(value = "确认人姓名")
    private String confirmUserName;
    /**
     * 单据(确认)状态
     */
    @ApiModelProperty(value = "单据(确认)状态")
    private String confirmState;
    @ApiModelProperty(value = "单据(确认)状态")
    private String confirmStateName;
    @ApiModelProperty(value = "单据(确认)理由")
    private String confirmOpinion;
    @ApiModelProperty(value = "单据(确认)状态时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date confirmTime;
    /**
     * 调出厂(仓库)编号
     */
    @ApiModelProperty(value = "调出厂(仓库)编号")
    private String outCode;
    @ApiModelProperty(value = "调出厂(仓库)")
    private String outName;
    /**
     * 调入厂（仓库）编号
     */
    @ApiModelProperty(value = "调入厂（仓库）编号")
    private String inCode;
    @ApiModelProperty(value = "调入厂（仓库）")
    private String inName;
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
     * 订货地址
     */
    @ApiModelProperty(value = "订货地址")
    private String orderAddress;

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
