package com.example.project.demos.web.dto.list;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

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
	@ApiModelProperty(value = "业务类型")
	private String functionId;
	@ApiModelProperty(value = "业务类型")
	private String functionName;
	/**
	 * 操作动作
	 */
	@ApiModelProperty(value = "操作动作")
	private String operationType;
	@ApiModelProperty(value = "操作动作")
	private String operationTypeName;
	/**
	 * 操作人
	 */
	@ApiModelProperty(value = "操作人")
	private String userCode;
	@ApiModelProperty(value = "操作人")
	private String userName;
	/**
	 * 操作时间
	 */
	@ApiModelProperty(value = "操作时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date operationTime;
	/**
	 * 操作内容说明
	 */
	@ApiModelProperty(value = "操作内容")
	private String operationInfo;
	/**
	 * 操作结果 000000-成功; 其他-失败
	 */
	@ApiModelProperty(value = "操作结果")
	private String operationResult;
	@ApiModelProperty(value = "操作结果")
	private String operationResultName;
	/**
	 * 操作结果说明
	 */
	@ApiModelProperty(value = "操作结果说明")
	private String operationMsg;
	@ApiModelProperty(value = "IP")
	private String loginIp;
	@ApiModelProperty(value = "token")
	private String token;

}
