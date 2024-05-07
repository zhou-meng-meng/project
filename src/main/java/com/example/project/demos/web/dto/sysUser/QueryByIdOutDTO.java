package com.example.project.demos.web.dto.sysUser;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class QueryByIdOutDTO {

    private static final long serialVersionUID = -89026850526790880L;
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
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

    @ApiModelProperty(value = "用户性别（0男 1女 2未知）")
    private String sexName;
    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "上次修改密码日期")
    private Date lastPasswordDate;
    /**
     * 帐号状态（0正常 1离职）
     */
    @ApiModelProperty(value = "帐号状态（0正常 1离职）")
    private String status;

    @ApiModelProperty(value = "帐号状态（0正常 1离职）")
    private String statusName;
    /**
     * 最后登录IP
     */
    @ApiModelProperty(value = "最后登录IP")
    private String loginIp;
    /**
     * 最后登录时间
     */
    @ApiModelProperty(value = "最后登录时间")
    private Date loginDate;
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
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    private String createBy;
    @ApiModelProperty(value = "创建人名称")
    private String createName;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**
     * 更新者
     */
    @ApiModelProperty(value = "修改人")
    private String updateBy;
    @ApiModelProperty(value = "修改人名称")
    private String updateName;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
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
