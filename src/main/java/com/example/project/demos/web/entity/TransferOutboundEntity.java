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
 * 调拨出库表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-11 16:09:49
 */
@Data
@TableName("transfer_outbound")
public class TransferOutboundEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
	@TableId
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	/**
	 * 物料编号
	 */
	private String materialCode;
	/**
	 * 调拨数量
	 */
	private BigDecimal transferCount;
	/**
	 * 调拨类型 0-原材料；1-产量
	 */
	private String transferType;
	/**
	 * 调拨日期
	 */
	private String transferDate;

	/**
	 * 单据号
	 */
	private String billNo;
	private String confirmUser;
	/**
	 * 单据(确认)状态
	 */
	private String confirmState;

	/**
	 * 单据(确认)状态时间
	 */
	private Date confirmTime;

	/**
	 * 单据(确认)理由
	 */
	private String confirmOpinion;
	/**
	 * 调出厂(仓库)编号
	 */
	private String outCode;
	/**
	 * 调入厂（仓库）编号
	 */
	private String inCode;
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
