package com.example.project.demos.web.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-25 15:30:37
 */
@Data
@TableName("supply_customer_pay")
public class SupplyCustomerPayEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
	@TableId
	private Long id;
	/**
	 * 来料入库主键
	 */
	private Long incomeId;
	/**
	 * 客户编号
	 */
	private String customerCode;
	/**
	 * 物料编号
	 */
	private String materialCode;
	/**
	 * 单价
	 */
	private BigDecimal unitPrice;
	/**
	 * 来料日期
	 */
	private Date incomeDate;
	/**
	 * 来料数量
	 */
	private BigDecimal incomeCount;
	/**
	 * 总金额
	 */
	private BigDecimal tollAmount;
	private String functionType;

	/**
	 * 创建人登录名
	 */
	private String createBy;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改人登录名
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

	public SupplyCustomerPayEntity(Long incomeId,String customerCode,String materialCode,BigDecimal unitPrice,BigDecimal incomeCount,BigDecimal tollAmount,Date incomeDate,String functionType){
		this.incomeId = incomeId;
		this.customerCode = customerCode;
		this.materialCode = materialCode;
		this.unitPrice = unitPrice;
		this.incomeCount = incomeCount;
		this.tollAmount = tollAmount;
		this.incomeDate = incomeDate;
		this.functionType = functionType;
	}

}
