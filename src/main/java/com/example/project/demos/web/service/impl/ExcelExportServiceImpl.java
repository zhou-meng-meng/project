package com.example.project.demos.web.service.impl;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.example.project.demos.web.dao.GuaFinancialDebtDao;
import com.example.project.demos.web.entity.GuaFinancialDebtEntity;
import com.example.project.demos.web.enums.ExcelExportEnum;
import com.example.project.demos.web.service.AbstractExcelExportService;
import com.example.project.demos.web.service.ExcelExportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("excelExportService")
public class ExcelExportServiceImpl extends AbstractExcelExportService implements ExcelExportService {

    @Resource
    GuaFinancialDebtDao guaFinancialDebtDao;

    @Override
    public InputStream getTemplateStream(String type) {
        ClassPathResource resource = new ClassPathResource(ExcelExportEnum.getEnum(type).getEnumName());
        InputStream inputStream = null;
        try {
            inputStream = resource.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    /**
     * 导出excel
     * @param type
     * @param map
     * @param response
     * @param beanName
     */
    @Override
    public void exportExcel(String type, Map<String, Object> map, HttpServletResponse response, String beanName) {
        this.exportData(type, map,response,beanName);
    }

    /**
     * 填充数据
     * @param excelWriter
     * @param writeSheet
     * @param type
     * @param map
     */
    @Override
    public void fillData(ExcelWriter excelWriter, WriteSheet writeSheet, String type, Map<String, Object> map) {
        packData(type,map,excelWriter,writeSheet);
    }

    /**
     * 组装数据调度
     * @param type
     * @param map
     * @param excelWriter
     * @param writeSheet
     * @return
     */
    @Override
    public void packData(String type,Map<String, Object> map,ExcelWriter excelWriter, WriteSheet writeSheet) {
        switch(type) {
            case "G1":
                packData1(map,excelWriter,writeSheet);
                break;
            case "G2":
                packData2(map,excelWriter,writeSheet);
                break;
            case "G3":
                packData3(map,excelWriter,writeSheet);
                break;
            case "G4":
                packData4(map,excelWriter,writeSheet);
                break;
            case "G5":
                packData5(map,excelWriter,writeSheet);
                break;
            case "G6":
                packData6(map,excelWriter,writeSheet);
                break;
            case "G7":
                packData7(map,excelWriter,writeSheet);
                break;
            case "G8":
                packData8(map,excelWriter,writeSheet);
                break;
            default:
                packData1(map,excelWriter,writeSheet);
        }
    }


    /**
     * 资产总账-组装数据
     * @param map
     * @param excelWriter
     * @param writeSheet
     */
    public void packData1(Map<String, Object> map,ExcelWriter excelWriter, WriteSheet writeSheet) {
        /*AssetLedgerBo assetLedgerBo = new AssetLedgerBo();
        excelWriter.fill(assetDetailService.getAssetStatusLedger(assetLedgerBo), writeSheet);*/
    }

    /**
     * 增量趋势-组装数据
     * @param map
     * @param excelWriter
     * @param writeSheet
     */
    public void packData2(Map<String, Object> map,ExcelWriter excelWriter, WriteSheet writeSheet) {
        /*AssetLedgerBo assetLedgerBo = BeanUtil.fillBeanWithMap(map, new AssetLedgerBo(), false);
        String year =  DateUtil.today().substring(0,4);
        if(map.containsKey("year")){
            year =  String.valueOf(map.get("year"));
        }
        Map<String,Object> map1 = new HashMap<>();
        map1.put("year",year);
        assetLedgerBo.setYear(year);
        excelWriter.fill(assetDetailService.getAssetAddNum(assetLedgerBo), writeSheet);
        excelWriter.fill(map1, writeSheet);*/
    }

    /**
     * 计划使用-组装数据
     * @param map
     * @param excelWriter
     * @param writeSheet
     */
    public void packData3(Map<String, Object> map,ExcelWriter excelWriter, WriteSheet writeSheet) {
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
        FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
        excelWriter.fill(valueMap, writeSheet);
        excelWriter.fill(list,fillConfig, writeSheet);*/
    }

    /**
     * 部门资产-组装数据
     * @param map
     * @param excelWriter
     * @param writeSheet
     */
    public void packData4(Map<String, Object> map,ExcelWriter excelWriter, WriteSheet writeSheet) {
        /*AssetLedgerBo assetLedgerBo = BeanUtil.fillBeanWithMap(map, new AssetLedgerBo(), false);
        AssetDeptNumVo assetDeptNumVo = assetDetailService.getAssetDeptNum(assetLedgerBo);
        List<Map<String, Object>> mapList = assetDeptNumVo.getAsset();
        List<AssetDeptVo> assetDeptVoList = assetDeptNumVo.getDept();
        List<List<String>> data = new ArrayList();
        String headStr = "资产类别,资产名称,规格型号,资产编号";
        List<String> headerList = Convert.toList(String.class, headStr);
        for (AssetDeptVo deptVo : assetDeptVoList) {
            headerList.add(deptVo.getLabel());
        }
        headerList.add("总计");
        data.add(headerList);
        for (Map<String, Object> map1 : mapList) {
            List<String> row = new ArrayList<>();
            row.add(String.valueOf(map1.get("assetKind")));
            row.add(String.valueOf(map1.get("assetName")));
            row.add(String.valueOf(map1.get("assetModel")));
            row.add(String.valueOf(map1.get("assetNo")));
            for (AssetDeptVo deptVo : assetDeptVoList) {
                row.add(String.valueOf(map1.get(deptVo.getProp())));
            }
            row.add(String.valueOf(map1.get("totalNum")));
            data.add(row);
        }
        excelWriter.write(data, writeSheet);*/
    }

    private void packData5(Map<String, Object> map, ExcelWriter excelWriter, WriteSheet writeSheet) {
        /*AssetInventoryTaskVo assetInventoryTaskVo = BeanUtil.fillBeanWithMap(map, new AssetInventoryTaskVo(), false);
        AssetInventoryTaskVo result = assetInventoryTaskService.getResult(assetInventoryTaskVo.getId());
        List<AssetInventoryItemVo> assetInventoryItemVos = result.getAssetInventoryItemVos();
        if (CollectionUtils.isEmpty(assetInventoryItemVos)) {
            assetInventoryItemVos = Lists.newArrayList();
        }
        for (int i = 0; i < assetInventoryItemVos.size(); i++) {
            assetInventoryItemVos.get(i).setNum(String.valueOf(i + 1));
        }
        AssetInventoryItemVo assetInventoryItemVo1 = new AssetInventoryItemVo();
        assetInventoryItemVo1.setNum("盘点人：");
        assetInventoryItemVo1.setCateName(assetInventoryTaskVo.getInventoryOper());
        assetInventoryItemVo1.setUseUnit("资产承接单位负责人：");
        assetInventoryItemVos.add(assetInventoryItemVo1);
        AssetInventoryItemVo assetInventoryItemVo2 = new AssetInventoryItemVo();
        assetInventoryItemVo2.setNum("监盘人：");
        assetInventoryItemVo1.setCateName(assetInventoryTaskVo.getMonitorOper());
        assetInventoryItemVos.add(assetInventoryItemVo2);
        excelWriter.fill(assetInventoryItemVos, writeSheet);
        Map<String, Object> map1 = new HashMap<>();
        map1.put("endDate", new SimpleDateFormat("yyyy-MM-dd").format(result.getEndDate()));
        excelWriter.fill(map1, writeSheet);*/
    }


    /**
     * 使用计划汇总
     * @param map
     * @param excelWriter
     * @param writeSheet
     */
    public void packData6(Map<String, Object> map,ExcelWriter excelWriter, WriteSheet writeSheet) {
        /*AssetLedgerBo assetLedgerBo = BeanUtil.fillBeanWithMap(map, new AssetLedgerBo(), false);
        AssetDeptNumVo assetDeptNumVo = assetPlanService.getAssetDeptPlanNum(assetLedgerBo);
        List<Map<String, Object>> mapList = assetDeptNumVo.getAsset();
        List<AssetDeptVo> assetDeptVoList = assetDeptNumVo.getDept();
        List<List<String>> data = new ArrayList();
        String headStr = "资产名称,规格型号";
        List<String> headerList = Convert.toList(String.class, headStr);
        for (AssetDeptVo deptVo : assetDeptVoList) {
            headerList.add(deptVo.getLabel());
        }
        headerList.add("总计");
        data.add(headerList);
        for (Map<String, Object> map1 : mapList) {
            List<String> row = new ArrayList<>();
            //row.add(String.valueOf(map1.get("assetKind")));
            row.add(String.valueOf(map1.get("assetName")));
            row.add(String.valueOf(map1.get("assetModel")));
            //row.add(String.valueOf(map1.get("assetNo")));
            for (AssetDeptVo deptVo : assetDeptVoList) {
                row.add(String.valueOf(map1.get(deptVo.getProp())));
            }
            row.add(String.valueOf(map1.get("totalNum")));
            data.add(row);
        }
        excelWriter.write(data, writeSheet);*/
    }

    /**
     * 部门资产额度查询
     * @param map
     * @param excelWriter
     * @param writeSheet
     */
    public void packData7(Map<String, Object> map,ExcelWriter excelWriter, WriteSheet writeSheet) {
        /*AssetLedgerBo assetLedgerBo = BeanUtil.fillBeanWithMap(map, new AssetLedgerBo(), false);
        AssetDeptNumVo assetDeptNumVo = assetPlanItemService.getAssetDeptLimit(assetLedgerBo);
        List<Map<String, Object>> mapList = assetDeptNumVo.getAsset();
        List<AssetDeptVo> assetDeptVoList = assetDeptNumVo.getDept();
        List<List<String>> data = new ArrayList();
        String headStr = "资产类别,资产名称,规格型号,资产编号";
        List<String> headerList = Convert.toList(String.class, headStr);
        for (AssetDeptVo deptVo : assetDeptVoList) {
            headerList.add(deptVo.getLabel());
        }
        headerList.add("总计");
        data.add(headerList);
        for (Map<String, Object> map1 : mapList) {
            List<String> row = new ArrayList<>();
            row.add(String.valueOf(map1.get("assetKind")));
            row.add(String.valueOf(map1.get("assetName")));
            row.add(String.valueOf(map1.get("assetModel")));
            row.add(String.valueOf(map1.get("assetNo")));
            for (AssetDeptVo deptVo : assetDeptVoList) {
                row.add(String.valueOf(map1.get(deptVo.getProp())));
            }
            row.add(String.valueOf(map1.get("totalNum")));
            data.add(row);
        }
        excelWriter.write(data, writeSheet);*/
    }

    public void packData8(Map<String, Object> map,ExcelWriter excelWriter, WriteSheet writeSheet) {
        /*AssetLedgerBo assetLedgerBo = BeanUtil.fillBeanWithMap(map, new AssetLedgerBo(), false);
        AssetDeptNumVo assetDeptNumVo = assetPlanItemService.getAssetDeptLimit(assetLedgerBo);
        List<Map<String, Object>> mapList = assetDeptNumVo.getAsset();
        List<AssetDeptVo> assetDeptVoList = assetDeptNumVo.getDept();
        List<List<String>> data = new ArrayList();
        String headStr = "资产类别,资产名称,规格型号,资产编号";
        List<String> headerList = Convert.toList(String.class, headStr);
        for (AssetDeptVo deptVo : assetDeptVoList) {
            headerList.add(deptVo.getLabel());
        }
        headerList.add("总计");
        data.add(headerList);
        for (Map<String, Object> map1 : mapList) {
            List<String> row = new ArrayList<>();
            row.add(String.valueOf(map1.get("assetKind")));
            row.add(String.valueOf(map1.get("assetName")));
            row.add(String.valueOf(map1.get("assetModel")));
            row.add(String.valueOf(map1.get("assetNo")));
            for (AssetDeptVo deptVo : assetDeptVoList) {
                row.add(String.valueOf(map1.get(deptVo.getProp())));
            }
            row.add(String.valueOf(map1.get("totalNum")));
            data.add(row);
        }
        excelWriter.write(data, writeSheet);*/
    }
}
