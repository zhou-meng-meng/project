package com.example.project.demos.web.dto.salesOutbound;

import com.example.project.demos.web.dto.list.UploadFileId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class AddDTO {

    /**
     * 物料编号
     */
    @ApiModelProperty(value = "物料编号")
    private String materialCode;
    @ApiModelProperty(value = "物料名称")
    private String materialName;

    /**
     * 出库数量
     */
    @ApiModelProperty(value = "出库数量")
    private BigDecimal outCount;
    /**
     * 单价
     */
    @ApiModelProperty(value = "单价")
    private BigDecimal unitPrice;
    /**
     * 总金额
     */
    @ApiModelProperty(value = "总金额")
    private BigDecimal tollAmount;
    @ApiModelProperty(value = "购货客户编号")
    private String customerCode;
    @ApiModelProperty(value = "购货客户名称")
    private String customerName;

    /**
     * 出库方编号
     */
    @ApiModelProperty(value = "出库方编号")
    private String outCode;
    @ApiModelProperty(value = "出库方名称")
    private String outName;

    /**
     * 销售员英文名
     */
    @ApiModelProperty(value = "销售员英文名")
    private String saler;

    /**
     * 销售时间
     */
    @ApiModelProperty(value = "销售时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date saleTime;

    /**
     * 单据号
     */
    @ApiModelProperty(value = "单据号")
    private String billNo;
    /**
     * 单据状态
     */
    @ApiModelProperty(value = "单据状态")
    private String billState;
    /**
     * 运输方式
     */
    @ApiModelProperty(value = "运输方式")
    private String transportType;
    @ApiModelProperty(value = "运输方式")
    private String transportTypeName;
    /**
     * 运费
     */
    @ApiModelProperty(value = "运费")
    private BigDecimal freight;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "附件主键集合")
    private List<UploadFileId> fileIdList;
}
