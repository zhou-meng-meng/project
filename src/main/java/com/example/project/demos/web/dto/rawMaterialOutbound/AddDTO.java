package com.example.project.demos.web.dto.rawMaterialOutbound;


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
     * 单价
     */
    @ApiModelProperty(value = "单价")
    private BigDecimal unitPrice;
    /**
     * 出库数量
     */
    @ApiModelProperty(value = "出库数量")
    private BigDecimal count;
    /**
     * 总金额
     */
    @ApiModelProperty(value = "总金额")
    private BigDecimal tollAmount;
    /**
     * 出库方编号
     */
    @ApiModelProperty(value = "出库方编号")
    private String outCode;
    @ApiModelProperty(value = "出库方名称")
    private String outName;

    /**
     * 领用人
     */
    @ApiModelProperty(value = "领用人")
    private String receiver;
    @ApiModelProperty(value = "领用人名称")
    private String receiverName;

    /**
     * 领用时间
     */
    @ApiModelProperty(value = "领用时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date receiveTime;
    /**
     * 单据号
     */
    @ApiModelProperty(value = "单据号")
    private String billNo;
    /**
     * 审批状态
     */
    @ApiModelProperty(value = "审批状态")
    private String approveState;
    /**
     * 单据状态
     */
    @ApiModelProperty(value = "单据状态")
    private String billState;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "附件主键集合")
    private List<UploadFileId> fileIdList;
}
