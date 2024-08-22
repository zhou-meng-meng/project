package com.example.project.demos.web.dto.list;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 审核队列表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-26 14:30:10
 */
@Data
public class ApproveOperationQueueInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
	@ApiModelProperty(value = "自增主键")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	/**
	 * 审核流水id
	 */
	@ApiModelProperty(value = "审核流水id")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long operationFlowId;
	/**
	 * 各审核业务主键
	 */
	@ApiModelProperty(value = "各审核业务主键")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long businessId;
	/**
	 * 审核人英文名
	 */
	@ApiModelProperty(value = "审核人英文名")
	private String approveUser;
	@ApiModelProperty(value = "审核人姓名")
	private String approveUserName;
	/**
	 * 业务类型编码
	 */
	@ApiModelProperty(value = "业务类型编码")
	private String functionId;
	@ApiModelProperty(value = "业务类型名称")
	private String functionName;

	@ApiModelProperty(value = "客户编号")
	private String customerCode;
	@ApiModelProperty(value = "客户名称")
	private String customerName;
	@ApiModelProperty(value = "物料编号")
	private String materialCode;
	@ApiModelProperty(value = "物料名称")
	private String materialName;
	@ApiModelProperty(value = "数量")
	private BigDecimal materialCount;


	/**
	 * 提交人
	 */
	@ApiModelProperty(value = "提交人")
	private String submitUser;
	@ApiModelProperty(value = "提交人姓名")
	private String submitUserName;
	/**
	 * 提交时间
	 */
	@ApiModelProperty(value = "提交时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date submitTime;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;

}
