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
 * 确认流水表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-26 14:30:10
 */
@Data
public class ConfirmOperationFlowInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
	@ApiModelProperty(value = "自增主键")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	@ApiModelProperty(value = "各审核业务主键")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long businessId;
	/**
	 * 业务类型编码
	 */
	@ApiModelProperty(value = "业务类型编码")
	private String functionId;
	@ApiModelProperty(value = "业务类型名称")
	private String functionName;
	/**
	 * 审核流水主键
	 */
	@ApiModelProperty(value = "审核流水主键")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long approveOperationId;
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
	@ApiModelProperty(value = "审核时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date approveTime;
	/**
	 * 审核状态
	 */
	@ApiModelProperty(value = "审核状态")
	private String approveState;
	@ApiModelProperty(value = "审核状态")
	private String approveStateName;
	/**
	 * 审核意见
	 */
	@ApiModelProperty(value = "审核意见")
	private String approveOpinion;
	@ApiModelProperty(value = "确认状态")
	private String confirmState;
	@ApiModelProperty(value = "确认状态")
	private String confirmStateName;
	@ApiModelProperty(value = "确认意见")
	private String confirmOpinion;
	/**
	 * 确认人英文名
	 */
	@ApiModelProperty(value = "确认人英文名")
	private String confirmUser;
	@ApiModelProperty(value = "确认人姓名")
	private String confirmUserName;
	/**
	 * 确认时间
	 */
	@ApiModelProperty(value = "确认时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date confirmTime;
	/**
	 * 创建者
	 */
	@ApiModelProperty(value = "创建者英文名")
	private String createBy;
	@ApiModelProperty(value = "创建者名字")
	private String createByName;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/**
	 * 更新者
	 */
	@ApiModelProperty(value = "更新者英文名")
	private String updateBy;
	@ApiModelProperty(value = "更新者名字")
	private String updateByName;
	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;

}
