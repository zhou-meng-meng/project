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
@TableName("sales_customer_pay")
public class SalesCustomerPayEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
	@TableId
	private Long id;
	/**
	 * 销售出库主键
	 */
	private Long saleId;
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
	 * 销售日期
	 */
	private Date saleDate;
	/**
	 * 销售数量
	 */
	private BigDecimal saleCount;
	/**
	 * 总金额
	 */
	private BigDecimal tollAmount;
	/**
	 * 运费
	 */
	private BigDecimal freight;
	/**
	 * 业务类型
	 */
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
	/**
	 * 显示标识 0-显示 1-不显示
	 */
	private String showFlag;

	public SalesCustomerPayEntity(Long id,Long saleId,String customerCode,String materialCode,BigDecimal unitPrice,BigDecimal saleCount,BigDecimal tollAmount,Date saleDate,String functionType){
		this.id = id;
		this.saleId = saleId;
		this.customerCode = customerCode;
		this.materialCode = materialCode;
		this.unitPrice = unitPrice;
		this.saleCount = saleCount;
		this.tollAmount = tollAmount;
		this.saleDate = saleDate;
		this.functionType = functionType;
	}

	public SalesCustomerPayEntity(){}

}
