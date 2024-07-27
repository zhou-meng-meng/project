package com.example.project.demos.web.dto.list;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 上传附件信息表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-07-18 13:45:31
 */
@Data
public class UploadFileInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "主键")
	private Long id;
	/**
	 * 业务主键
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "业务主键")
	private Long businessId;
	/**
	 * 文件原名称
	 */
	@ApiModelProperty(value = "文件原名称")
	private String fileOriginalName;

	/**
	 * 文件名称
	 */
	@ApiModelProperty(value = "文件名称")
	private String fileFullName;
	@ApiModelProperty(value = "文件类型")
	private String fileType;
	/**
	 * 文件路径
	 */
	@ApiModelProperty(value = "文件路径")
	private String filePath;
	/**
	 * 业务编码
	 */
	@ApiModelProperty(value = "业务编码")
	private String functionId;
	/**
	 * 上传人
	 */
	@ApiModelProperty(value = "上传人")
	private String createBy;
	@ApiModelProperty(value = "上传人姓名")
	private String createByName;
	/**
	 * 上传时间
	 */
	@ApiModelProperty(value = "上传时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/**
	 * 修改人
	 */
	@ApiModelProperty(value = "修改人")
	private String updateBy;
	/**
	 * 修改时间
	 */
	@ApiModelProperty(value = "修改时间")
	private Date updateTime;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;

}
