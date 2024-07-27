package com.example.project.demos.web.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("confirm_operation_flow")
public class ConfirmOperationFlowEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
	@TableId
	private Long id;
	/**
	 * 各确认业务主键
	 */
	private Long businessId;
	/**
	 * 业务类型编码
	 */
	private String functionId;
	/**
	 * 审核流水主键
	 */
	private Long approveOperationId;
	/**
	 * 提交人编号
	 */
	private String submitUser;
	/**
	 * 提交时间
	 */
	private Date submitTime;
	/**
	 * 审核人
	 */
	private String approveUser;
	/**
	 * 审核时间
	 */
	private Date approveTime;
	/**
	 * 审核状态
	 */
	private String approveState;
	/**
	 * 审核意见
	 */
	private String approveOpinion;
	private String confirmState;
	/**
	 * 确认人英文名
	 */
	private String confirmUser;
	/**
	 * 确认时间
	 */
	private Date confirmTime;
	private String confirmOpinion;
	/**
	 * 创建人英文名
	 */
	private String createBy;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改人英文名
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

	public ConfirmOperationFlowEntity(Long id,Long businessId,String functionId,Long approveOperationId,String submitUser,Date submitTime,String approveUser,Date approveTime,String approveState,String approveOpinion,String confirmState,String remark){
		this.id = id;
		this.businessId = businessId;
		this.functionId = functionId;
		this.approveOperationId = approveOperationId;
		this.submitUser = submitUser;
		this.submitTime = submitTime;
		this.approveUser = approveUser;
		this.approveState = approveState;
		this.approveTime = approveTime;
		this.approveOpinion = approveOpinion;
		this.confirmState = confirmState;
		this.remark = remark;
	}

}
