package com.example.project.demos.web.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 供货商客户维护表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-04-24 13:42:58
 */
@Data
@TableName("customer_supply")
public class CustomerSupplyEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 客户编号
	 */
	@TableId
	private String id;
	/**
	 * 客户名称
	 */
	private String name;
	/**
	 * 客户类型 0-公户；1-个体
	 */
	private String type;
	/**
	 * 联系人
	 */
	private String linkUser;
	/**
	 * 联系电话
	 */
	private String linkPhoneNo;
	/**
	 * 客户地址
	 */
	private String address;
	/**
	 * 客户等级
	 */
	private String level;
	/**
	 * 客户邮箱
	 */
	private String email;
	/**
	 * 微信号
	 */
	private String wechatNo;
	/**
	 * 传真
	 */
	private String faxNo;
	/**
	 * 所属销售
	 */
	private String saler;
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
