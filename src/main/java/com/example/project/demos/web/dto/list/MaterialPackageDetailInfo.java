package com.example.project.demos.web.dto.list;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 物料装袋表-明细
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-09 09:26:13
 */
@Data
public class MaterialPackageDetailInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
	@ApiModelProperty(value = "主键")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	/**
	 * 装袋表主键
	 */
	@ApiModelProperty(value = "装袋表主键")
	private Long packageId;
	/**
	 * 物料枚举值
	 */
	@ApiModelProperty(value = "物料枚举值")
	private String materialCode;
	@ApiModelProperty(value = "物料名称")
	private String materialName;
	/**
	 * 每锅重量
	 */
	@ApiModelProperty(value = "每锅重量")
	private BigDecimal potWeight;
	/**
	 * 创建者
	 */
	@ApiModelProperty(value = "创建者英文名")
	private String createBy;
	@ApiModelProperty(value = "创建者名字")
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
