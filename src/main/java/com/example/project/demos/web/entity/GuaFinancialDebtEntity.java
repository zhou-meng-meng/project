package com.example.project.demos.web.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 金融报表-融资担保公司资产负债情况表（G3）
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-07-10 09:58:25
 */
@Data
@TableName("gua_financial_debt")
public class GuaFinancialDebtEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
	@TableId
	private Long id;

	/**
	 * 月份
	 */
	private String fillingMonth;
	/**
	 * 填报人
	 */
	private String fillingOper;
	/**
	 * 填表时间
	 */
	private Date fillingTime;
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
