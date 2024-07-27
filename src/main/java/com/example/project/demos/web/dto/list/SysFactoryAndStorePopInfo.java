package com.example.project.demos.web.dto.list;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * 工厂/仓库弹窗表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-04-24 13:42:58
 */
@Data
public class SysFactoryAndStorePopInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 厂区编号
	 */
	@ApiModelProperty(value = "编号")
	private String code;
	/**
	 * 厂区名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 厂区负责人电话
	 */
	@ApiModelProperty(value = "负责人电话")
	private String manageTel;

	@ApiModelProperty(value = "负责人姓名")
	private String manageName;


}
