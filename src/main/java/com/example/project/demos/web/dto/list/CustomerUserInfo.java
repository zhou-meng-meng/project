package com.example.project.demos.web.dto.list;

import lombok.Data;

@Data
public class CustomerUserInfo {
    private Long id;
    /**
     * 客户ID
     */
    private String customerId;
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