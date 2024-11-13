package com.example.project.demos.web.dto.list;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 销售/供应商记录的客户信息
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-26 14:30:10
 */
@Data
public class CustomerPayInfo implements Serializable {
	private static final long serialVersionUID = 1L;


	/**
	 * 客户编号  客户名称
	 */
	private String customerCode;
	private String customerName;

}
