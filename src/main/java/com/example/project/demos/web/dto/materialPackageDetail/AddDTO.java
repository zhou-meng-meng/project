package com.example.project.demos.web.dto.materialPackageDetail;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class AddDTO {

    /**
     * 物料枚举值
     */
    @ApiModelProperty(value = "物料枚举值")
    private String materialCode;

    /**
     * 每锅重量
     */
    @ApiModelProperty(value = "每锅重量")
    private BigDecimal potWeight;
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
}
