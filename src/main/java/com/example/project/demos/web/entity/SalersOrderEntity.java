package com.example.project.demos.web.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 业务员下单表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-06-11 10:33:39
 */
@Data
@TableName("salers_order")
public class SalersOrderEntity implements Serializable {
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
	 * 单据号
	 */
	private String billCode;
	/**
	 * 客户编号
	 */
	private String customerCode;
	/**
	 * 联系人
	 */
	private String customerLink;
	/**
	 * 客户联系方式
	 */
	private String customerTel;
	/**
	 * 装车日期
	 */
	private Date loadDate;
	/**
	 * 装车数量
	 */
	private BigDecimal loadNum;
	/**
	 * 单价
	 */
	private BigDecimal unitPrice;
	private BigDecimal tollAmount;
	/**
	 * 装车方式
	 */
	private String loadType;
	/**
	 * 出库方编码
	 */
	private String outCode;
	private String saler;
	/**
	 * 审批状态
	 */
	private String approveState;
	/**
	 * 审核人
	 */
	private String approveUser;
	/**
	 * 审核意见
	 */
	private String approveOpinion;
	/**
	 * 审核时间
	 */
	private Date approveTime;
	/**
	 * 确认状态
	 */
	private String confirmState;
	/**
	 * 确认人
	 */
	private String confirmUser;
	/**
	 * 确认意见
	 */
	private String confirmOpinion;
	/**
	 * 确认时间
	 */
	private Date confirmTime;
	/**
	 * 单据状态
	 */
	private String billState;
	/**
	 * 汇款
	 */
	private BigDecimal remit;
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
