package com.example.project.demos.web.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 销售出库表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-24 14:38:26
 */
@Data
@TableName("sales_outbound")
public class SalesOutboundEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
	@TableId
	private Long id;
	/**
	 * 物料编号
	 */
	private String materialCode;
	/**
	 * 出库数量
	 */
	private BigDecimal outCount;
	/**
	 * 单价
	 */
	private BigDecimal unitPrice;
	/**
	 * 总金额
	 */
	private BigDecimal tollAmount;
	/**
	 * 购货客户编号
	 */
	private String customerCode;
	/**
	 * 出库方编号
	 */
	private String outCode;
	/**
	 * 销售员英文名
	 */
	private String saler;
	/**
	 * 销售时间
	 */
	private Date saleTime;
	/**
	 * 审批状态
	 */
	private String approveState;
	private Date approveTime;
	/**
	 * 审批意见
	 */
	private String approveOpinion;
	/**
	 * 单据号
	 */
	private String billNo;
	/**
	 * 单据状态
	 */
	private String billState;
	/**
	 * 运输方式
	 */
	private String transportType;
	/**
	 * 运费
	 */
	private BigDecimal freight;
	/**
	 * 回款金额
	 */
	private BigDecimal receivePayment;
	/**
	 * 回款时间
	 */
	private Date receivePaymentTime;
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
