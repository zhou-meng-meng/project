package com.example.project.demos.web.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 客户用户表(CustomerUser)实体类
 *
 * @author makejava
 * @since 2024-02-26 20:37:53
 */
@Data
@TableName("customer_user")
public class CustomerUser implements Serializable {
    private static final long serialVersionUID = -89026850526790880L;
    /**
     * 客户ID
     */
    private Long id;
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 客户地址
     */
    private String customerAddress;
    /**
     * 客户电话
     */
    private String customerTel;


}

