package com.example.project.demos.web.dto.sysDept;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class EditDTO {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

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
     * 显示顺序
     */
    @ApiModelProperty(value = "显示顺序")
    private String orderNum;
    /**
     * 负责人
     */
    @ApiModelProperty(value = "负责人登录名")
    private String leader;
    @ApiModelProperty(value = "负责人姓名")
    private String leaderName;

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
