package com.example.project.demos.web.dto.sysDept;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DeleteByIdDTO {
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

    @ApiModelProperty(value = "负责人姓名")
    private String leaderName;
    @ApiModelProperty(value = "联系电话")
    private String phoneNo;
}
