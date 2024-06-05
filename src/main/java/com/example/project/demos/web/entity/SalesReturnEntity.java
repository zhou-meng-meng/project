package com.example.project.demos.web.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-23 13:55:47
 */
@Data
@TableName("sales_return")
public class SalesReturnEntity implements Serializable {
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
	 * 物料编号
	 */
	private String materialCode;
	/**
	 * 单价
	 */
	private BigDecimal unitPrice;
	/**
	 * 退回数量
	 */
	private BigDecimal returnCount;
	/**
	 * 退回总金额
	 */
	private BigDecimal tollAmount;
	/**
	 * 审批状态
	 */
	private String approveState;
	private Date approveTime;
	/**
	 * 审批意见
	 */
	private String approveOpinion;

	@ApiModelProperty(value = "确认状态")
	private String confirmState;
	@ApiModelProperty(value = "确认时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date confirmTime;
	@ApiModelProperty(value = "确认意见")
	private String confirmOpinion;

	/**
	 * 单据号
	 */
	private String billNo;
	/**
	 * 单据状态
	 */
	private String billState;
	/**
	 * 退回方编号
	 */
	private String inCode;
	private String returnType;
	/**
	 * 退回人
	 */
	private String returnUser;
	/**
	 * 退回时间
	 */
	private Date returnTime;
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
	 * 修改人
	 */
	private Date updateTime;
	/**
	 * 备注
	 */
	private String remark;

}
