package com.example.project.demos.web.dto.sysUser;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class AddDTO {

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

    /**
     * 部门ID
     */
    @ApiModelProperty(value = "部门ID")
    private String deptId;

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
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;

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
    private Date entryDate;
    /**
     * 离职日期
     */
    @ApiModelProperty(value = "离职日期")
    private Date leaveDate;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
}
