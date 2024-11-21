package com.example.project.demos.web.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("approve_operation_queue")
public class ApproveOperationQueueEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
	@TableId
	private Long id;
	/**
	 * 审核流水id
	 */
	private Long operationFlowId;
	/**
	 * 各审核业务主键
	 */
	private Long businessId;
	/**
	 * 审核人英文名
	 */
	private String approveUser;
	/**
	 * 业务类型编码
	 */
	private String functionId;

	/**
	 * 客户编号
	 */
	private String customerCode;
	/**
	 * 物料编号
	 */
	private String materialCode;
	/**
	 * 数量
	 */
	private BigDecimal materialCount;

	/**
	 * 提交人
	 */
	private String submitUser;
	/**
	 * 提交时间
	 */
	private Date submitTime;
	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 录入页面选择的来料/销售时间
	 */
	private Date enterDate;

	/**
	 * 新增使用
	 */
	public ApproveOperationQueueEntity(Long id,Long operationFlowId,Long businessId,String functionId,String approveUser,String customerCode,String materialCode,BigDecimal materialCount,String submitUser,Date submitTime,String remark,Date enterDate){
		this.id = id;
		this.operationFlowId = operationFlowId;
		this.businessId = businessId;
		this.functionId = functionId;
		this.approveUser = approveUser;
		this.customerCode = customerCode;
		this.materialCode = materialCode;
		this.materialCount = materialCount;
		this.submitUser = submitUser;
		this.submitTime = submitTime;
		this.remark = remark;
		this.enterDate = enterDate;
	}

}
