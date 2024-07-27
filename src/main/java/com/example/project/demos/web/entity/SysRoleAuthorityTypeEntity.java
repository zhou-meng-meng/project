package com.example.project.demos.web.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 角色权限类型表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-25 12:36:42
 */
@Data
@TableName("sys_role_authority_type")
public class SysRoleAuthorityTypeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
	@TableId
	private Long id;
	/**
	 * 角色编号
	 */
	private String roleId;
	/**
	 * 角色权限类型
	 */
	private String roleAuthorityType;
	/**
	 * 创建人
	 */
	private String createBy;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改人
	 */
	private String updateBy;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 备注
	 */
	private String remark;

}
