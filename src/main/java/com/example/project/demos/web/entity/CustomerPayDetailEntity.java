package com.example.project.demos.web.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 客户往来账明细
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-10 09:46:07
 */
@Data
@TableName("customer_pay_detail")
public class CustomerPayDetailEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
	@TableId
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	/**
	 * 客户编号
	 */
	private String customerCode;
	/**
	 * 账面余额
	 */
	private BigDecimal bookBalance;
	/**
	 * 单价
	 */
	private BigDecimal unitPrice;
	/**
	 * 数量
	 */
	private BigDecimal materialCount;
	/**
	 * 物料余额
	 */
	private BigDecimal materialBalance;

	/**
	 * 打款金额
	 */
	private BigDecimal payBalance;
	/**
	 * 退回金额
	 */
	private BigDecimal returnBalance;
	/**
	 * 折扣金额
	 */
	private BigDecimal discountBalance;
	/**
	 * 付款类型  入款 出款
	 */
	private String payType;
	private String operatorBy;
	private String isDefault;
	/**
	 * 创建人/经办人
	 */
	private String createBy;
	/**
	 * 创建时间/经办时间
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
