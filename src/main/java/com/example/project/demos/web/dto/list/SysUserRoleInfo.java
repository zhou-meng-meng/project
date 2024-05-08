package com.example.project.demos.web.dto.list;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户和角色关联表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-04-24 13:42:58
 */
@Data
public class SysUserRoleInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键")
	private Long id;
	/**
	 * 用户ID
	 */
	@ApiModelProperty(value = "用户登录名")
	private String userLogin;
	/**
	 * 角色ID
	 */
	@ApiModelProperty(value = "角色ID")
	private String roleId;
	@ApiModelProperty(value = "角色名称")
	private String roleName;

}
