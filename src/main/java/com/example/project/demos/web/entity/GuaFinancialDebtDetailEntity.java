package com.example.project.demos.web.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-07-10 09:58:25
 */
@Data
@TableName("gua_financial_debt_detail")
public class GuaFinancialDebtDetailEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
	@TableId
	private Long id;
	private Long debtId;
	/**
	 * 项目
	 */
	@ExcelProperty(index =0)
	private String projectName;
	/**
	 * 代码
	 */
	@ExcelProperty(index =1)
	private String projectCode;
	/**
	 * 国有控股 融资担保机构
	 */
	@ExcelProperty(index =2)
	private String countryAmount;
	/**
	 * 民营及外资融资担保机构
	 */
	@ExcelProperty(index =3)
	private String otherAmount;
	/**
	 * 合计
	 */
	@ExcelProperty(index =4)
	private String tollAmount;

	private int orderNum;
}
