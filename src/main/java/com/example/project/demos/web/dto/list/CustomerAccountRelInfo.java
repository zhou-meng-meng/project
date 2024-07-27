package com.example.project.demos.web.dto.list;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 客户账号对应关系表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-09 16:36:41
 */
@Data
public class CustomerAccountRelInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */

	@ApiModelProperty(value = "自增主键")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	/**
	 * 客户编号
	 */
	@ApiModelProperty(value = "客户编号")
	private String customerCode;
	/**
	 * 账号
	 */
	@ApiModelProperty(value = "账号")
	private String accountNo;
	/**
	 * 账户名称
	 */
	@ApiModelProperty(value = "账户名称")
	private String accountName;
	/**
	 * 开户行行号
	 */
	@ApiModelProperty(value = "开户行行号")
	private String openBankNo;
	/**
	 * 开户行名称
	 */
	@ApiModelProperty(value = "开户行名称")
	private String openBankName;
	/**
	 * 账号状态
	 */
	@ApiModelProperty(value = "账号状态(字典值)")
	private String status;
	@ApiModelProperty(value = "账号状态")
	private String statusName;

	@ApiModelProperty(value = "创建人")
	private String createBy;
	@ApiModelProperty(value = "创建人")
	private String createByName;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date updateTime;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;

}
