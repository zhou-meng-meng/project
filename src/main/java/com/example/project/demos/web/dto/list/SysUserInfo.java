package com.example.project.demos.web.dto.list;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-04-24 13:42:58
 */
@Data
public class SysUserInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	/**
	 * 用户工号
	 */
	@ExcelProperty(value = "用户工号")
	@ApiModelProperty(value = "用户工号")
	private String userId;
	/**
	 * 用户英文登录名
	 */
	@ExcelProperty(value = "用户登录名")
	@ApiModelProperty(value = "用户登录名")
	private String userLogin;
	/**
	 * 用户姓名
	 */
	@ExcelProperty(value = "用户姓名")
	@ApiModelProperty(value = "用户姓名")
	private String userName;
	/**
	 * 用户类型（（0-总公司;1-厂区;2-仓库））
	 */
	@ApiModelProperty(value = "用户类型（（0-总公司;1-厂区;2-仓库））")
	private String userType;
	@ExcelProperty(value = "用户类型")
	@ApiModelProperty(value = "用户类型（（0-总公司;1-厂区;2-仓库））")
	private String userTypeName;
	/**
	 * 部门ID
	 */
	@ApiModelProperty(value = "部门ID")
	private String deptId;
	@ExcelProperty(value = "部门名称")
	@ApiModelProperty(value = "部门名称")
	private String deptName;
	/**
	 * 用户邮箱
	 */
	@ExcelProperty(value = "用户邮箱")
	@ApiModelProperty(value = "用户邮箱")
	private String email;
	/**
	 * 微信号
	 */
	@ExcelProperty(value = "微信号")
	@ApiModelProperty(value = "微信号")
	private String wechatNo;
	/**
	 * 手机号码
	 */
	@ExcelProperty(value = "手机号码")
	@ApiModelProperty(value = "手机号码")
	private String phoneNo;
	/**
	 * 用户性别（0男 1女 2未知）
	 */
	@ApiModelProperty(value = "用户性别（0男 1女 2未知）")
	private String sex;
	@ExcelProperty(value = "用户性别")
	@ApiModelProperty(value = "用户性别（0男 1女 2未知）")
	private String sexName;

	/**
	 * 角色编码
	 */
	@ApiModelProperty(value = "角色编码")
	private String roleId;
	@ExcelProperty(value = "角色名称")
	@ApiModelProperty(value = "角色名称")
	private String roleName;

	/**
	 * 角色单价权限 Y-是;N-否
	 */
	@ExcelProperty(value = "角色单价权限")
	@ApiModelProperty(value = "角色单价权限 Y-是;N-否")
	private String isPriceEdit;

	/**
	 * 密码
	 */
	@ApiModelProperty(value = "密码")
	private String password;
	@ExcelProperty(value = "上次修改密码时间")
	@ApiModelProperty(value = "上次修改密码时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastPasswordDate;
	/**
	 * 帐号状态（0正常 1离职）
	 */
	@ApiModelProperty(value = "帐号状态（0正常 1离职）")
	private String status;
	@ExcelProperty(value = "帐号状态")
	@ApiModelProperty(value = "帐号状态（0正常 1离职）")
	private String statusName;
	/**
	 * 最后登录IP
	 */
	@ExcelProperty(value = "最后登录IP")
	@ApiModelProperty(value = "最后登录IP")
	private String loginIp;
	/**
	 * 最后登录时间
	 */
	@ExcelProperty(value = "最后登录时间")
	@ApiModelProperty(value = "最后登录时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date loginDate;
	/**
	 * 显示顺序
	 */
	@ApiModelProperty(value = "显示顺序")
	private String orderNum;
	/**
	 * 紧急联系人
	 */
	@ExcelProperty(value = "紧急联系人")
	@ApiModelProperty(value = "紧急联系人")
	private String urgentName;
	/**
	 * 紧急联系人电话
	 */
	@ExcelProperty(value = "紧急联系人电话")
	@ApiModelProperty(value = "紧急联系人电话")
	private String urgentPhone;

	/**
	 * 入职日期
	 */
	@ExcelProperty(value = "入职日期")
	@ApiModelProperty(value = "入职日期")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date entryDate;

	@ExcelProperty(value = "社保日期")
	@ApiModelProperty(value = "社保日期")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date insuranceDate;
	/**
	 * 离职日期
	 */
	@ExcelProperty(value = "离职日期")
	@ApiModelProperty(value = "离职日期")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date leaveDate;
	/**
	 * 创建者
	 */
	@ApiModelProperty(value = "创建者")
	private String createBy;
	@ExcelProperty(value = "创建人名称")
	@ApiModelProperty(value = "创建人名称")
	private String createByName;
	/**
	 * 创建时间
	 */
	@ExcelProperty(value = "创建时间")
	@ApiModelProperty(value = "创建时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/**
	 * 更新者
	 */
	@ApiModelProperty(value = "修改人")
	private String updateBy;
	@ExcelProperty(value = "修改人名称")
	@ApiModelProperty(value = "修改人名称")
	private String updateByName;
	/**
	 * 更新时间
	 */
	@ExcelProperty(value = "更新时间")
	@ApiModelProperty(value = "更新时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
	/**
	 * 备注
	 */
	@ExcelProperty(value = "备注")
	@ApiModelProperty(value = "备注")
	private String remark;

}
