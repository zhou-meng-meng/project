package com.example.project.demos.web.dto.salesReturn;

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
    @ApiModelProperty(value = "客户编号")
    private String customerCode;
    @ApiModelProperty(value = "客户名称")
    private String customerName;
    /**
     * 物料编号
     */
    @ApiModelProperty(value = "物料编号")
    private String materialCode;
    @ApiModelProperty(value = "物料名称")
    private String materialName;

    /**
     * 退回数量
     */
    @ApiModelProperty(value = "退回数量")
    private BigDecimal returnCount;

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
     * 退回方编号
     */
    @ApiModelProperty(value = "退回方编号")
    private String inCode;
    @ApiModelProperty(value = "退回方名称")
    private String inName;
    /**
     * 退回人
     */
    @ApiModelProperty(value = "退回人")
    private String returnUser;
    @ApiModelProperty(value = "退回人姓名")
    private String returnUserName;

    /**
     * 退货时间
     */
    @ApiModelProperty(value = "退货时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date returnTime;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "附件主键集合")
    private List<UploadFileId> fileIdList;
}
