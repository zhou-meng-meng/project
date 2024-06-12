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
import java.util.List;

/**
 * 物料产出装袋表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-09 09:26:13
 */
@Data
public class MaterialPackageInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
	@ApiModelProperty(value = "主键")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	/**
	 * 厂区编码
	 */
	@ApiModelProperty(value = "厂区编码")
	private String factoryCode;
	@ApiModelProperty(value = "厂区名称")
	private String factoryName;
	/**
	 * 机子号
	 */
	@ApiModelProperty(value = "机子号")
	private String machineCode;
	@ApiModelProperty(value = "机器号")
	private String machineName;
	/**
	 * 日期
	 */
	@ApiModelProperty(value = "日期")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date packageDate;
	/**
	 * 班次
	 */
	@ApiModelProperty(value = "班次枚举值")
	private String dutyCode;
	@ApiModelProperty(value = "班次名称")
	private String dutyName;
	/**
	 * 锅数
	 */
	@ApiModelProperty(value = "锅数")
	private BigDecimal potNum;
	@ApiModelProperty(value = "每袋重量")
	private BigDecimal packageWeight;
	/**
	 * 合计重量
	 */
	@ApiModelProperty(value = "合计重量")
	private BigDecimal tollWeight;
	/**
	 * 应出袋数
	 */
	@ApiModelProperty(value = "应出袋数")
	private BigDecimal shouldNum;
	/**
	 * 实际袋数
	 */
	@ApiModelProperty(value = "实际袋数")
	private BigDecimal actualNum;
	/**
	 * 差额
	 */
	@ApiModelProperty(value = "差额")
	private BigDecimal balanceNum;

	@ApiModelProperty(value = "原料集合")
	List<MaterialPackageDetailInfo> detailInfoList;

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
