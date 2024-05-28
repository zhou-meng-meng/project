package com.example.project.demos.web.dto.sysDept;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AddDTO {

    /**
     * 部门id
     */
    @ApiModelProperty(value = "部门id")
    private String deptId;

    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称")
    private String deptName;

    /**
     * 父部门id
     */
    @ApiModelProperty(value = "父部门id")
    private String parentId;
    /**
     * 父部门名称
     */
    @ApiModelProperty(value = "父部门名称")
    private String parentName;
    /**
     * 显示顺序
     */
    @ApiModelProperty(value = "显示顺序")
    private String orderNum;
    /**
     * 负责人
     */
    @ApiModelProperty(value = "负责人登录名")
    private String leader;
    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    private String phoneNo;

    /**
     * 状态（0正常 1停用）
     */
    @ApiModelProperty(value = "状态（0正常 1停用）")
    private String status;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
}
