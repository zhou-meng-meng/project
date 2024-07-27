package com.example.project.demos.web.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @date 2024-05-23 10:56:52
 */
@Data
@TableName("supply_return")
public class SupplyReturnEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
	@TableId
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

	@ApiModelProperty(value = "客户编号")
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
	 * 总金额
	 */
	private BigDecimal tollAmount;
	private String approveUser;
	/**
	 * 审核状态
	 */
	private String approveState;
	/**
	 * 审批意见
	 */
	private String approveOpinion;
	private Date approveTime;
	/**
	 * 单据号
	 */
	private String billNo;
	/**
	 * 单据状态
	 */
	private String billState;
	/**
	 * 退回入库方编号
	 */
	private String outCode;
	/**
	 * 退回人
	 */
	private String returnUser;
	/**
	 * 退货时间
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
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 备注
	 */
	private String remark;

}
