package com.example.project.demos.web.dto.customerUser;

import lombok.Data;

@Data
public class EditDTO {
    /**
     * ID
     */
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
