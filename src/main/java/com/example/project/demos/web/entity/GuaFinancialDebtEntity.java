package com.example.project.demos.web.entity;

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
	 * 表标题
	 */
	private String tableTitle;
	/**
	 * 表号
	 */
	private String tableNo;
	/**
	 * 制表机关
	 */
	private String tableOffice;
	/**
	 * 数据单位
	 */
	private String dataUnit;
	/**
	 * 填报单位
	 */
	private String fillingDept;
	/**
	 * 年份
	 */
	private String fillingYear;
	/**
	 * 统计负责人
	 */
	private String statisticsManager;
	/**
	 * 填表人
	 */
	private String statisticsOper;
	/**
	 * 报出日期
	 */
	private String reportDate;
	/**
	 * 导入时间
	 */
	private Date importDate;

}
