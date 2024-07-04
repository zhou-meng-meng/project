package com.example.project.demos.web.dto.list;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 销售方客户维护表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-04-24 13:42:58
 */
@Data
public class CustomerSaleInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = ToStringSerializer.class)
	@ExcelIgnore
	@ApiModelProperty(value = "主键")
	private Long id;
	/**
	 * 客户编号
	 */
	@ExcelProperty(value = "客户编号")
	@ApiModelProperty(value = "客户编号")
	private String code;
	/**
	 * 客户名称
	 */
	@ExcelProperty(value = "客户名称")
	@ApiModelProperty(value = "客户名称")
	private String name;
	/**
	 * 客户类型 0-公户；1-个体
	 */
	@ExcelIgnore
	@ApiModelProperty(value = "客户类型 0-公户；1-个体")
	private String type;
	@ExcelProperty(value = "客户类型")
	@ApiModelProperty(value = "客户类型")
	private String typeName;

	/**
	 * 客户证件类型 字典值
	 */
	@ExcelIgnore
	@ApiModelProperty(value = "客户证件类型")
	private String certType;
	@ExcelProperty(value = "客户证件类型")
	@ApiModelProperty(value = "客户证件类型")
	private String certTypeName;

	/**
	 * 客户证件号码
	 */
	@ExcelProperty(value = "客户证件号码")
	@ApiModelProperty(value = "客户证件号码")
	private String certNo;

	/**
	 * 联系人
	 */
	@ExcelProperty(value = "联系人")
	@ApiModelProperty(value = "联系人")
	private String linkUser;
	/**
	 * 联系电话
	 */
	@ExcelProperty(value = "联系电话")
	@ApiModelProperty(value = "联系电话")
	private String linkPhoneNo;
	/**
	 * 客户地址
	 */
	@ExcelProperty(value = "客户地址")
	@ApiModelProperty(value = "客户地址")
	private String address;
	/**
	 * 客户等级
	 */
	@ExcelIgnore
	@ApiModelProperty(value = "客户等级")
	private String level;
	@ExcelProperty(value = "客户等级")
	@ApiModelProperty(value = "客户等级")
	private String levelName;
	/**
	 * 客户邮箱
	 */
	@ExcelProperty(value = "邮箱")
	@ApiModelProperty(value = "客户邮箱")
	private String email;
	/**
	 * 微信号
	 */
	@ExcelProperty(value = "微信号")
	@ApiModelProperty(value = "微信号")
	private String wechatNo;
	/**
	 * 传真
	 */
	@ExcelProperty(value = "传真")
	@ApiModelProperty(value = "传真")
	private String faxNo;
	/**
	 * 所属销售
	 */
	@ExcelIgnore
	@ApiModelProperty(value = "所属销售")
	private String saler;
	@ExcelProperty(value = "所属销售")
	@ApiModelProperty(value = "所属销售")
	private String salerName;

	@ExcelIgnore
	@ApiModelProperty(value = "审核状态编码")
	private String approveState;

	@ExcelProperty(value = "审核状态")
	@ApiModelProperty(value = "审核状态")
	private String approveStateName;

	@ExcelIgnore
	@ApiModelProperty(value = "审核人编码")
	private String approveUser;
	@ExcelProperty(value = "审核人")
	@ApiModelProperty(value = "审核人")
	private String approveUserName;
	@ExcelProperty(value = "审核时间")
	@ApiModelProperty(value = "审核时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date approveTime;
	@ExcelProperty(value = "审核意见")
	@ApiModelProperty(value = "审核意见")
	private String approveOpinion;

	/**
	 * 创建者
	 */
	@ExcelIgnore
	@ApiModelProperty(value = "创建者英文名")
	private String createBy;
	@ExcelProperty(value = "创建者名字")
	@ApiModelProperty(value = "创建者名字")
	private String createByName;
	/**
	 * 创建时间
	 */
	@ExcelProperty(value = "创建时间")
	@ApiModelProperty(value = "创建时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;
	/**
	 * 更新者
	 */
	@ExcelIgnore
	@ApiModelProperty(value = "更新者英文名")
	private String updateBy;
	@ExcelProperty(value = "更新者名字")
	@ApiModelProperty(value = "更新者名字")
	private String updateByName;
	/**
	 * 更新时间
	 */
	@ExcelProperty(value = "更新时间")
	@ApiModelProperty(value = "更新时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date updateTime;
	/**
	 * 备注
	 */
	@ExcelProperty(value = "备注")
	@ApiModelProperty(value = "备注")
	private String remark;

}
