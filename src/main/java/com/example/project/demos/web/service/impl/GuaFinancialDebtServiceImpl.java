package com.example.project.demos.web.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.example.project.demos.web.dao.GuaFinancialDebtDao;
import com.example.project.demos.web.entity.GuaFinancialDebtEntity;
import com.example.project.demos.web.service.GuaFinancialDebtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service("guaFinancialDebtService")
public class GuaFinancialDebtServiceImpl  implements GuaFinancialDebtService {

    @Resource
    GuaFinancialDebtDao guaFinancialDebtDao;


    @Override
    public void export() {
        /*log.info("开始主表数据");
        GuaFinancialDebtEntity debtEntity = guaFinancialDebtDao.selectById(1810916194734170113L);
        log.info("开始获取详细数据");
        List<GuaFinancialDebtDetailEntity> list =  guaFinancialDebtDetailDao.queryList(1810916194734170113L);
        Map<String, Object> valueMap = new HashMap<>();
        //填报单位
        valueMap.put("fillingDept",debtEntity.getFillingDept());
        //填报年月
        valueMap.put("fillingYear",debtEntity.getFillingYear());
        //统计负责人
        valueMap.put("statisticsManager",debtEntity.getStatisticsManager());
        //填表人
        valueMap.put("statisticsOper",debtEntity.getStatisticsOper());
        //报出日期
        valueMap.put("reportDate",debtEntity.getReportDate());
        String outName="D://G3.xlsx";
        String fileName = "classpath:template/G3.xlsx";
        // 这里注意 入参用了forceNewRow 代表在写入list的时候不管list下面有没有空行 都会创建一行，然后下面的数据往后移动。默认 是false，会直接使用下一行，如果没有则创建。
        // forceNewRow 如果设置了true,有个缺点 就是他会把所有的数据都放到内存了，所以慎用
        // 简单的说 如果你的模板有list,且list不是最后一行，下面还有数据需要填充 就必须设置 forceNewRow=true 但是这个就会把所有数据放到内存 会很耗内存
        FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
        ExcelWriter excelWriter = EasyExcel.write(outName).withTemplate(fileName).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        excelWriter.fill(list, fillConfig,writeSheet);
        excelWriter.fill(valueMap, writeSheet);
        excelWriter.finish();*/

    }
}
