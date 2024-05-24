package com.example.project.demos.web.dto.list;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 菜单权限表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-04-24 13:42:58
 */
@Data
public class SysLogInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	/**
	 * 操作类型
	 */
	private String functionId;
	private String functionName;
	/**
	 * 操作动作
	 */
	private String operationType;
	private String operationTypeName;
	/**
	 * 操作人
	 */
	private String userCode;
	private String userName;
	/**
	 * 操作时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date operationTime;
	/**
	 * 操作内容说明
	 */
	private String operationInfo;
	/**
	 * 操作结果 S-成功; F-失败
	 */
	private String operationResult;
	private String operationResultName;
	/**
	 * 操作结果说明
	 */
	private String operationMsg;

}
