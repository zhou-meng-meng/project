package com.example.project.demos.web.controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.example.project.demos.web.dao.GuaFinancialDebtDao;
import com.example.project.demos.web.dao.GuaFinancialDebtDetailDao;
import com.example.project.demos.web.entity.GuaFinancialDebtDetailEntity;
import com.example.project.demos.web.listener.GuaFinancialDebtListener;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;

/**
 * 融资担保公司资产负债情况表（G3）
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-08 15:30:25
 */
@RestController
@RequestMapping("guaFinancialDebt")
@Api(tags="融资担保公司资产负债情况表（G3）")
public class GuaFinancialDebtController {

    @Resource
    private GuaFinancialDebtDao guaFinancialDebtDao;

    @Resource
    private GuaFinancialDebtDetailDao guaFinancialDebtDetailDao;

    @PostMapping("/importData")
    @ApiOperation("导入数据")
    public void importData(HttpServletRequest request, MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            /**
             *DynamicEasyExcelListener1:导入监听类
             * 1：是正文起始行，用于处理合并单元格
             **/
            GuaFinancialDebtListener listener1 = new GuaFinancialDebtListener(request,0,guaFinancialDebtDao,guaFinancialDebtDetailDao);

            //读取全部sheet使用extraRead(CellExtraTypeEnum.MERGE).doReadAll()
            /**
             * headRowNumber(1)：1是正文起始行
             * CellExtraTypeEnum.MERGE：读取合并单元格
             * CellExtraTypeEnum.COMMENT：读取批注
             * CellExtraTypeEnum.HYPERLINK：读取超链接
             * extraRead(CellExtraTypeEnum.MERGE).doReadAll()：如果多sheet，直接使用这个方法
             **/
            EasyExcel.read(inputStream, GuaFinancialDebtDetailEntity.class,listener1).headRowNumber(0).extraRead(CellExtraTypeEnum.MERGE).sheet(0).doRead();
            inputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
