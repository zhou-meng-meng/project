package com.example.project.demos.web.dto.sysUser;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserLoginOutDTO {
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
     * 用户性别（0男 1女 2未知）
     */
    @ApiModelProperty(value = "用户性别（0男 1女 2未知）")
    private String sex;

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
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 是否初始密码
     */
    @ApiModelProperty(value = "是否初始密码 y-是;N-否")
    private String isInitePwd;

    /**
     * 密码是否过期
     */
    @ApiModelProperty(value = "密码是否过期 Y-是;N-否")
    private String isOverDuePwd;

    /**
     * 权限菜单集合
     */
    @ApiModelProperty(value = "权限菜单集合")
    private List<String> menuList;

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
