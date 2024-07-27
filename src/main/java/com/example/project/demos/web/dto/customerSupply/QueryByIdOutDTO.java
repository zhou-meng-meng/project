package com.example.project.demos.web.dto.customerSupply;

import com.example.project.demos.web.dto.list.CustomerAccountRelInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class QueryByIdOutDTO {

    private static final long serialVersionUID = -89026850526790880L;
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "主键")
    private Long id;
    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    private String code;
    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    private String name;
    /**
     * 客户类型 0-公户；1-个体
     */
    @ApiModelProperty(value = "客户类型 0-公户；1-个体")
    private String type;

    /**
     * 客户证件类型 字典值
     */
    @ApiModelProperty(value = "客户证件类型")
    private String certType;

    /**
     * 客户证件号码
     */
    @ApiModelProperty(value = "客户证件号码")
    private String certNo;

    /**
     * 联系人
     */
    @ApiModelProperty(value = "联系人")
    private String linkUser;
    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    private String linkPhoneNo;
    /**
     * 客户地址
     */
    @ApiModelProperty(value = "客户地址")
    private String address;
    /**
     * 客户等级
     */
    @ApiModelProperty(value = "客户等级")
    private String level;
    /**
     * 客户邮箱
     */
    @ApiModelProperty(value = "客户邮箱")
    private String email;
    /**
     * 微信号
     */
    @ApiModelProperty(value = "微信号")
    private String wechatNo;
    /**
     * 传真
     */

    @ApiModelProperty(value = "传真")
    private String faxNo;
    /**
     * 所属销售
     */
    @ApiModelProperty(value = "所属销售")
    private String saler;
    @ApiModelProperty(value = "所属销售")
    private String salerName;

    @ApiModelProperty(value = "客户账户对应集合")
    private List<CustomerAccountRelInfo> accountRelList;

    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者英文名")
    private String createBy;

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

    /**
     * 操作结果编码:null
     */
    @ApiModelProperty(value = "操作结果编码")
    private String errorCode;

    /**
     * 操作结果信息:null
     */
    @ApiModelProperty(value = "操作结果信息")
    private String errorMsg;
}
