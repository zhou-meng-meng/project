package com.example.project.demos.web.controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.example.project.demos.web.dao.GuaFinancialDebtDao;
import com.example.project.demos.web.dto.customerSupply.QueryByIdDTO;
import com.example.project.demos.web.dto.customerSupply.QueryByIdOutDTO;
import com.example.project.demos.web.entity.GuaFinancialDebtEntity;
import com.example.project.demos.web.listener.GuaFinancialDebtListener;
import com.example.project.demos.web.service.GuaFinancialDebtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
@Api(tags="华丽达-融资担保公司资产负债情况表（G3）")
public class GuaFinancialDebtController {

    @Resource
    private GuaFinancialDebtDao guaFinancialDebtDao;

    @Autowired
    private GuaFinancialDebtService guaFinancialDebtService;

    @PostMapping("/importData")
    @ApiOperation("导入数据")
    public void importData(HttpServletRequest request, MultipartFile file,String fillingMonth, String fillingOper) {
        try {
            InputStream inputStream = file.getInputStream();
            /**
             *DynamicEasyExcelListener1:导入监听类
             * 1：是正文起始行，用于处理合并单元格
             **/
            GuaFinancialDebtListener listener1 = new GuaFinancialDebtListener(request,0,guaFinancialDebtDao,fillingMonth,fillingOper);

            //读取全部sheet使用extraRead(CellExtraTypeEnum.MERGE).doReadAll()
            /**
             * headRowNumber(1)：1是正文起始行
             * CellExtraTypeEnum.MERGE：读取合并单元格
             * CellExtraTypeEnum.COMMENT：读取批注
             * CellExtraTypeEnum.HYPERLINK：读取超链接
             * extraRead(CellExtraTypeEnum.MERGE).doReadAll()：如果多sheet，直接使用这个方法
             **/
            EasyExcel.read(inputStream, GuaFinancialDebtEntity.class,listener1).headRowNumber(0).extraRead(CellExtraTypeEnum.MERGE).sheet(0).doRead();
            inputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 导出数据
     */

    @GetMapping("/export")
    @ApiOperation("导出Excel")
    public void export() {
        guaFinancialDebtService.export();
    }


}
