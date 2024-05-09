package com.example.project.demos.web.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户账号对应关系表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-09 16:36:41
 */
@Data
@TableName("customer_account_rel")
public class CustomerAccountRelEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
	@TableId
	private Long id;
	/**
	 * 客户编号
	 */
	private String customerCode;
	/**
	 * 账号
	 */
	private String accountNo;
	/**
	 * 账户名称
	 */
	private String accountName;
	/**
	 * 开户行行号
	 */
	private String openBankNo;
	/**
	 * 开户行名称
	 */
	private String openBankName;
	/**
	 * 账号状态
	 */
	private String status;
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
