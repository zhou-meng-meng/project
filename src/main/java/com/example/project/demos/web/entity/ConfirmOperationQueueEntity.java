package com.example.project.demos.web.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("confirm_operation_queue")
public class ConfirmOperationQueueEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
	@TableId
	private Long id;
	/**
	 * 确认流水id
	 */
	private Long confirmFlowId;
	/**
	 * 各确认业务主键
	 */
	private Long businessId;
	/**
	 * 确认人英文名
	 */
	private String confirmUser;
	/**
	 * 业务类型编码
	 */
	private String functionId;
	/**
	 * 提交人
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

}
