package com.example.project.demos.web.dto.sysUser;

import com.fasterxml.jackson.annotation.JsonFormat;
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
     * 用户工号
     */
    @ApiModelProperty(value = "用户工号")
    private String userId;
    /**
     * 用户英文登录名
     */
    @ApiModelProperty(value = "用户英文登录名")
    private String userLogin;
    /**
     * 用户姓名
     */
    @ApiModelProperty(value = "用户姓名")
    private String userName;
    /**
     * 用户类型（（0-总公司;1-厂区;2-仓库））
     */
    @ApiModelProperty(value = "用户类型（（0-总公司;1-厂区;2-仓库））")
    private String userType;
    @ApiModelProperty(value = "用户类型（（0-总公司;1-厂区;2-仓库））")
    private String userTypeName;

    /**
     * 角色编码
     */
    @ApiModelProperty(value = "角色编码")
    private String roleId;
    @ApiModelProperty(value = "角色名称")
    private String roleName;

    /**
     * 部门ID
     */
    @ApiModelProperty(value = "部门ID")
    private String deptId;
    @ApiModelProperty(value = "部门名称")
    private String deptName;

    /**
     * 用户邮箱
     */
    @ApiModelProperty(value = "用户邮箱")
    private String email;
    /**
     * 微信号
     */
    @ApiModelProperty(value = "微信号")
    private String wechatNo;
    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码")
    private String phoneNo;
    /**
     * 用户性别（0男 1女 2未知）
     */
    @ApiModelProperty(value = "用户性别（0男 1女 2未知）")
    private String sex;

    /**
     * 帐号状态（0正常 1离职）
     */
    @ApiModelProperty(value = "帐号状态（0正常 1离职）")
    private String status;

    /**
     * 显示顺序
     */
    @ApiModelProperty(value = "显示顺序")
    private String orderNum;
    /**
     * 紧急联系人
     */
    @ApiModelProperty(value = "紧急联系人")
    private String urgentName;
    /**
     * 紧急联系人电话
     */
    @ApiModelProperty(value = "紧急联系人电话")
    private String urgentPhone;

    /**
     * 入职日期
     */
    @ApiModelProperty(value = "入职日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date entryDate;
    @ApiModelProperty(value = "社保日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date insuranceDate;
    /**
     * 离职日期
     */
    @ApiModelProperty(value = "离职日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date leaveDate;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
}
