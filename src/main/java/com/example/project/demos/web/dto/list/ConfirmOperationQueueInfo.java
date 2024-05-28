package com.example.project.demos.web.dto.list;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 确认队列表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-26 14:30:10
 */
@Data
public class ConfirmOperationQueueInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
	@ApiModelProperty(value = "自增主键")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	/**
	 * 确认流水id
	 */
	@ApiModelProperty(value = "自增主键")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long confirmFlowId;
	/**
	 * 各确认业务主键
	 */
	@ApiModelProperty(value = "自增主键")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long businessId;
	/**
	 * 确认人英文名
	 */
	@ApiModelProperty(value = "确认人英文名")
	private String confirmUser;
	@ApiModelProperty(value = "确认人姓名")
	private String confirmUserName;
	/**
	 * 业务类型编码
	 */
	@ApiModelProperty(value = "业务类型编码")
	private String functionId;
	@ApiModelProperty(value = "业务类型名称")
	private String functionName;
	@ApiModelProperty(value = "提交人")
	private String submitUser;
	@ApiModelProperty(value = "提交人姓名")
	private String submitUserName;
	@ApiModelProperty(value = "提交时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date submitTime;
	/**
	 * 审核人
	 */
	@ApiModelProperty(value = "审核人英文名")
	private String approveUser;
	@ApiModelProperty(value = "审核人姓名")
	private String approveUserName;
	/**
	 * 审核时间
	 */
	@ApiModelProperty(value = "审核时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date approveTime;

}
