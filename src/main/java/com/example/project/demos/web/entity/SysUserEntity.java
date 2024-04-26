package com.example.project.demos.web.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("sys_user")
public class SysUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	@TableId
	private Long id;
	/**
	 * 用户英文登录名
	 */
	private String userLogin;
	/**
	 * 用户姓名
	 */
	private String userName;
	/**
	 * 用户类型（00系统用户）
	 */
	private String userType;
	/**
	 * 部门ID
	 */
	private String deptId;
	/**
	 * 用户邮箱
	 */
	private String email;
	/**
	 * 微信号
	 */
	private String wechatNo;
	/**
	 * 手机号码
	 */
	private String phoneNo;
	/**
	 * 用户性别（0男 1女 2未知）
	 */
	private String sex;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 帐号状态（0正常 1停用）
	 */
	private String status;
	/**
	 * 删除标志（0代表存在 2代表删除）
	 */
	private String delFlag;
	/**
	 * 最后登录IP
	 */
	private String loginIp;
	/**
	 * 最后登录时间
	 */
	private Date loginDate;
	/**
	 * 识别名
	 */
	private String distinguishedName;
	/**
	 * 显示顺序
	 */
	private String orderNum;
	/**
	 * 紧急联系人
	 */
	private String urgentName;
	/**
	 * 紧急联系人电话
	 */
	private String urgentPhone;
	/**
	 * 所属工厂
	 */
	private String factoryId;
	/**
	 * 入职日期
	 */
	private Date entryDate;
	/**
	 * 离职日期
	 */
	private Date leaveDate;
	/**
	 * 创建者
	 */
	private String createBy;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新者
	 */
	private String updateBy;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 备注
	 */
	private String remark;

}
