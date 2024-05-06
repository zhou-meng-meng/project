package com.example.project.demos.web.dto.sysDictType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EditDTO {
    /**
     * 字典主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;
    /**
     * 字典名称
     */
    @ApiModelProperty(value = "字典名称")
    private String dictName;
    /**
     * 字典类型
     */
    @ApiModelProperty(value = "字典类型")
    private String dictType;

    /**
     * 状态（0正常 1停用）
     */
    @ApiModelProperty(value = "状态（0正常 1停用）")
    private String status;

    @ApiModelProperty(value = "备注")
    private String remark;
}
