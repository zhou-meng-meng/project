package com.example.project.demos.web.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 审核流水表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-26 14:30:10
 */
@Data
@TableName("approve_operation_flow")
public class ApproveOperationFlowEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
	@TableId
	private Long id;
	/**
	 * 各审核业务主键
	 */
	private Long businessId;
	/**
	 * 业务类型编码
	 */
	private String functionId;
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

	/**
	 * 新增使用
	 * @param businessId
	 * @param functionId
	 * @param submitUser
	 * @param submitTime
	 */
	public ApproveOperationFlowEntity(Long id,Long businessId,String functionId,String submitUser,Date submitTime,String approveState,String remark){
		this.id = id;
		this.businessId = businessId;
		this.functionId = functionId;
		this.submitUser = submitUser;
		this.submitTime = submitTime;
		this.approveState = approveState;
		this.remark = remark;
	}




}
