package com.example.project.demos.web.dto.customerSupply;

import com.example.project.demos.web.dto.list.MaterialPackageDetailInfo;
import com.example.project.demos.web.entity.CustomerAccountRelEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class EditDTO {
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
    private String weChatNo;
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

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    private List<CustomerAccountRelEntity> list;
}
