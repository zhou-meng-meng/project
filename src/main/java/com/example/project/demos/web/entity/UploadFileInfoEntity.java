package com.example.project.demos.web.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("upload_file_info")
public class UploadFileInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
	@TableId
	private Long id;
	/**
	 * 业务主键
	 */
	private Long businessId;
	/**
	 * 文件原名称
	 */
	private String fileOriginalName;

	/**
	 * 文件名称
	 */
	private String fileFullName;
	/**
	 * 文件路径
	 */
	private String filePath;
	/**
	 * 业务编码
	 */
	private String functionId;
	/**
	 * 创建人
	 */
	private String createBy;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改人
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

}
