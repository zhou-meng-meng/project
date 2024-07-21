package com.example.project.demos.web.dto.list;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 上传附件信息表-各业务编辑页面反显使用
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-07-18 13:45:31
 */
@Data
public class UploadFileEditInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
	@ApiModelProperty(value = "主键")
	private Long id;

	/**
	 * 文件原名称
	 */
	@ApiModelProperty(value = "文件原名称")
	private String fileOriginalName;

	@ApiModelProperty(value = "base64str")
	private String base64Str;

	@ApiModelProperty(value = "文件类型")
	private String fileType;




}
