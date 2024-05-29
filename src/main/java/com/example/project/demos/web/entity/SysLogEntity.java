package com.example.project.demos.web.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-22 15:26:24
 */
@Data
@TableName("sys_log")
public class SysLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
	@TableId
	private Long id;
	/**
	 * 操作码
	 */
	private String functionId;
	/**
	 * 操作类型
	 */
	private String operationType;
	/**
	 * 操作人
	 */
	private String userCode;
	/**
	 * 操作时间
	 */
	private Date operationTime;
	/**
	 * 操作内容说明
	 */
	private String operationInfo;
	/**
	 * 操作结果 S-成功; F-失败
	 */
	private String operationResult;
	/**
	 * 操作结果说明
	 */
	private String operationMsg;

	/**
	 * IP
	 */
	private String loginIp;
	private String token;

}
