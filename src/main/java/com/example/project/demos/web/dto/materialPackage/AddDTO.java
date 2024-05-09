package com.example.project.demos.web.dto.materialPackage;

import com.example.project.demos.web.dto.list.MaterialPackageDetailInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class AddDTO {

    /**
     * 厂区编码
     */
    @ApiModelProperty(value = "厂区编码")
    private String factoryCode;

    /**
     * 机子号
     */
    @ApiModelProperty(value = "机子号")
    private String machineCode;
    /**
     * 日期
     */
    @ApiModelProperty(value = "日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date packageDate;
    /**
     * 班次
     */
    @ApiModelProperty(value = "班次枚举值")
    private String dutyCode;

    /**
     * 锅数
     */
    @ApiModelProperty(value = "锅数")
    private BigDecimal potNum;
    /**
     * 合计重量
     */
    @ApiModelProperty(value = "合计重量")
    private BigDecimal tollWeight;
    /**
     * 应出袋数
     */
    @ApiModelProperty(value = "应出袋数")
    private BigDecimal shouldNum;
    /**
     * 实际袋数
     */
    @ApiModelProperty(value = "实际袋数")
    private BigDecimal actualNum;
    /**
     * 差额
     */
    @ApiModelProperty(value = "差额")
    private BigDecimal balanceNum;

    @ApiModelProperty(value = "原料集合")
    List<MaterialPackageDetailInfo> detailInfoList;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
}