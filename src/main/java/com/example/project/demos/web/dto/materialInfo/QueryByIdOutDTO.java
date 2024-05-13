package com.example.project.demos.web.dto.materialInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
    @ApiModelProperty(value = "物料编号")
    private String code;
    /**
     * 物料名称
     */
    @ApiModelProperty(value = "物料名称")
    private String name;
    /**
     * 型号
     */
    @ApiModelProperty(value = "型号编号")
    private String model;

    /**
     * 型号名称
     */
    @ApiModelProperty(value = "型号名称")
    private String modelName;

    /**
     * 单位
     */
    @ApiModelProperty(value = "单位")
    private String unit;

    /**
     * 单位名称
     */
    @ApiModelProperty(value = "单位名称")
    private String unitName;

    /**
     * 物料类型  0-原材料；1-销售产品
     */
    @ApiModelProperty(value = "物料类型  0-原材料；1-销售产品")
    private String type;
    @ApiModelProperty(value = "物料类型  0-原材料；1-销售产品")
    private String typeName;
    /**
     * 供应商编号（来料使用）
     */
    @ApiModelProperty(value = "供应商编号（来料使用）")
    private String supplyerCode;
    @ApiModelProperty(value = "供应商名称（来料使用）")
    private String supplyerName;
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
