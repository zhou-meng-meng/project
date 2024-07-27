package com.example.project.demos.web.dto.transferOutbound;

import com.example.project.demos.web.dto.list.UploadFileId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class EditDTO {
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

    /**
     * 调拨数量
     */
    private BigDecimal transferCount;
    /**
     * 调拨类型 0-原材料；1-产量
     */
    @ApiModelProperty(value = "调拨类型 0-原材料；1-产量")
    private String transferType;


    @ApiModelProperty(value = "调拨日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date transferDate;

    /**
     * 单据号
     */
    @ApiModelProperty(value = "单据号")
    private String billNo;

    /**
     * 调出厂(仓库)编号
     */
    @ApiModelProperty(value = "调出厂(仓库)编号")
    private String outCode;
    @ApiModelProperty(value = "调出厂(仓库)名称")
    private String outName;

    /**
     * 调入厂（仓库）编号
     */
    @ApiModelProperty(value = "调入厂（仓库）编号")
    private String inCode;
    @ApiModelProperty(value = "调入厂（仓库）名称")
    private String inName;


    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "附件主键集合")
    private List<UploadFileId> fileIdList;
}
