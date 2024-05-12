package com.example.project.demos.web.dto.list;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-11 11:13:27
 */
@Data
public class RebuildOutboundInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
	@ApiModelProperty(value = "自增主键")
	private Long id;
	/**
	 * 物料编号
	 */
	@ApiModelProperty(value = "物料编号")
	private String materialCode;
	@ApiModelProperty(value = "物料名称")
	private String materialName;
	/**
	 * 重造出库数量
	 */
	@ApiModelProperty(value = "重造出库数量")
	private BigDecimal rebuildCount;
	/**
	 * 重造时间
	 */
	@ApiModelProperty(value = "重造时间")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date rebuildDate;
	/**
	 * 入库厂区编号
	 */
	@ApiModelProperty(value = "入库厂区编号")
	private String factoryCode;
	@ApiModelProperty(value = "入库厂区名称")
	private String factoryName;
	/**
	 * 单据号
	 */
	@ApiModelProperty(value = "自增主键")
	private String billNo;
	/**
	 * 单据状态
	 */
	@ApiModelProperty(value = "自增主键")
	private String billState;
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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;

}
