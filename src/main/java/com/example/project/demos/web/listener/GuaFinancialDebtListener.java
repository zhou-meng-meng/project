package com.example.project.demos.web.listener;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.example.project.demos.web.dao.GuaFinancialDebtDao;
import com.example.project.demos.web.dao.GuaFinancialDebtDetailDao;
import com.example.project.demos.web.entity.GuaFinancialDebtDetailEntity;
import com.example.project.demos.web.entity.GuaFinancialDebtEntity;
import com.example.project.demos.web.utils.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.*;

public class GuaFinancialDebtListener extends AnalysisEventListener<GuaFinancialDebtDetailEntity> {
    private static final Logger LOGGER = LoggerFactory.getLogger(GuaFinancialDebtListener.class);

    private HttpServletRequest request;

    private final GuaFinancialDebtDao guaFinancialDebtDao;
    private final GuaFinancialDebtDetailDao guaFinancialDebtDetailDao;

    /**
     * 表头数据（存储所有的表头数据）
     */
    private List<Map<Integer, String>> headList = new ArrayList<>();

    /**
     * 数据体
     */
    private List<Map<Integer, String>> dataList = new ArrayList<>();


    public GuaFinancialDebtListener(HttpServletRequest request, Integer headRowNumber,GuaFinancialDebtDao guaFinancialDebtDao,GuaFinancialDebtDetailDao guaFinancialDebtDetailDao) {
        this.request = request;
        this.headRowNumber = headRowNumber;
        this.guaFinancialDebtDao = SpringUtils.getBean(GuaFinancialDebtDao.class);
        this.guaFinancialDebtDetailDao = SpringUtils.getBean(GuaFinancialDebtDetailDao.class);
    }
    public GuaFinancialDebtListener() {
        this.guaFinancialDebtDao = SpringUtils.getBean(GuaFinancialDebtDao.class);
        this.guaFinancialDebtDetailDao = SpringUtils.getBean(GuaFinancialDebtDetailDao.class);
    }
    /**
     * 最终返回的解析数据list
     */
    private final List<GuaFinancialDebtDetailEntity> data = new ArrayList<>();
    /**
     * 解析数据
     * key是sheetName，value是相应sheet的解析数据
     */
    private final Map<String, List<GuaFinancialDebtDetailEntity>> dataMap = new HashMap<>();
    /**
     * 合并单元格
     * key键是sheetName，value是相应sheet的合并单元格数据
     */
    private final Map<String, List<CellExtra>> mergeMap = new HashMap<>();

    /**
     * 正文起始行
     */
    private Integer headRowNumber;

    /**
     * 这里会一行行的返回头信息，不包含数据
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        LOGGER.info("解析到一条头数据:{}", JSON.toJSONString(headMap));
        //存储全部表头数据
    }

    /**
     * 这个每一条数据解析都会来调用
     */
    @Override
    public void invoke(GuaFinancialDebtDetailEntity excelData1, AnalysisContext context) {
        LOGGER.info("解析到一条数据:{}", JSON.toJSONString(data));
        String sheetName = context.readSheetHolder().getSheetName();
        dataMap.computeIfAbsent(sheetName, k -> new ArrayList<>());
        //将所有的数据放入dataMap中
        dataMap.get(sheetName).add(excelData1);
//        data.add(excelData1);
    }

    /**
     * 上面写入的CellExtraTypeEnum.MERGE就会来调用这个方法
     * @param extra
     * @param context
     */
    @Override
    public void extra(CellExtra extra, AnalysisContext context) {
        //获取sheet名称
        String sheetName = context.readSheetHolder().getSheetName();
        switch (extra.getType()) {
            // 额外信息是合并单元格
            case MERGE:
                if (extra.getRowIndex() >= headRowNumber) {
                    mergeMap.computeIfAbsent(sheetName, k -> new ArrayList<>());
                    //key是sheet名称，value是合并单元格信息
                    mergeMap.get(sheetName).add(extra);
                }
                break;
            case COMMENT:
            case HYPERLINK:
            default:
        }
    }

    /**
     * 将具有多个sheet数据的dataMap转变成一个data
     */
    private void convertDataMapToData() {
        Iterator<Map.Entry<String, List<GuaFinancialDebtDetailEntity>>> iterator = dataMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, List<GuaFinancialDebtDetailEntity>> next = iterator.next();
            String sheetName = next.getKey();//sheet的名称
            List<GuaFinancialDebtDetailEntity> list = next.getValue();//sheet中的集合数据
            List<CellExtra> mergeList = mergeMap.get(sheetName);//合并单元格信息
            if (!CollectionUtils.isEmpty(mergeList)) {
                list = explainMergeData(list, mergeList);
            }
            data.addAll(list);
        }
    }

    /**
     * 处理有合并单元格的数据
     * @param list      解析的sheet数据
     * @param mergeList 合并单元格信息
     * @return 填充好的解析数据
     */
    private List<GuaFinancialDebtDetailEntity> explainMergeData(List<GuaFinancialDebtDetailEntity> list, List<CellExtra> mergeList) {
        // 循环所有合并单元格信息
        for (CellExtra item : mergeList) {
            Integer firstRowIndex = item.getFirstRowIndex() - headRowNumber;//起始行
            Integer lastRowIndex = item.getLastRowIndex() - headRowNumber;//结束行
            Integer firstColumnIndex = item.getFirstColumnIndex();//起始列
            Integer lastColumnIndex = item.getLastColumnIndex();//结束列
            // 获取初始值，即合并单元的的值
            Object initValue = getInitValueFromList(firstRowIndex, firstColumnIndex, list);
            // 设置值
            for (int i = firstRowIndex; i <= lastRowIndex; i++) {
                for (int j = firstColumnIndex; j <= lastColumnIndex; j++) {
                    setInitValueToList(initValue, i, j, list);
                }
            }
        }
        return list;
    }

    /**
     * 获取合并单元格的初始值
     * rowIndex对应list的索引
     * columnIndex对应实体内的字段
     *
     * @param firstRowIndex    起始行
     * @param firstColumnIndex 起始列
     * @param list             列数据
     * @return 初始值
     */
    private Object getInitValueFromList(Integer firstRowIndex, Integer firstColumnIndex, List<GuaFinancialDebtDetailEntity> list) {
        Object filedValue = null;
        GuaFinancialDebtDetailEntity object = list.get(firstRowIndex);
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            ExcelProperty annotation = field.getAnnotation(ExcelProperty.class);
            if (annotation != null) {
                if (annotation.index() == firstColumnIndex) {
                    try {
                        filedValue = field.get(object);
                        break;
                    } catch (IllegalAccessException e) {
                        LOGGER.error("获取合并单元格的初始值异常：" + e.getMessage());
                    }
                }
            }
        }
        return filedValue;
    }

    /**
     * 设置合并单元格的值
     *
     * @param filedValue  值
     * @param rowIndex    行
     * @param columnIndex 列
     * @param list        解析数据
     */
    public void setInitValueToList(Object filedValue, Integer rowIndex, Integer columnIndex, List<GuaFinancialDebtDetailEntity> list) {
        GuaFinancialDebtDetailEntity object = list.get(rowIndex);//
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);//提升反射性能，关闭安全检查
            ExcelProperty annotation = field.getAnnotation(ExcelProperty.class);
            if (annotation != null) {
                if (annotation.index() == columnIndex) {
                    try {
                        field.set(object, filedValue);
                        break;
                    } catch (IllegalAccessException e) {
                        LOGGER.error("设置合并单元格的值异常：" + e.getMessage());
                    }
                }
            }
        }
    }
    /**
     * 所有数据解析完成了 都会来调用
     *每个sheet调取一次
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        LOGGER.info("所有数据解析完成！"+data);
        //在invok解析完数据以后，才去处理合并单元格数据，最后得到合并完以后的数据
        convertDataMapToData();
        LOGGER.info("所有数据解析完成！"+data);
        LOGGER.info("开始准备插入数据！");
        /**
         * Excel的数据是从填报单位 年份这一行开始的  为第一行  list下标为0
         * 实际数据是从第二行开始的  list下标为1
         */
        //先插入主表数据
        GuaFinancialDebtEntity entity = new GuaFinancialDebtEntity();
        entity.setTableTitle("融资担保公司资产负债情况表");
        entity.setTableNo("G3表");
        entity.setTableOffice("山东省地方金融监督管理局");
        entity.setDataUnit("万元");
        entity.setFillingDept(data.get(0).getProjectName());
        entity.setFillingYear(data.get(0).getOtherAmount());
        entity.setStatisticsManager(data.get(24).getProjectName());
        entity.setStatisticsOper(data.get(24).getProjectCode());
        entity.setReportDate(data.get(24).getTollAmount());
        entity.setImportDate(new Date());
        guaFinancialDebtDao.insert(entity);
        Long id = entity.getId();
        int num = 1;
        for(GuaFinancialDebtDetailEntity detailEntity : data){
            detailEntity.setDebtId(id);
            detailEntity.setOrderNum(num);
            num ++ ;
        }
        //开始插入明细表
        List<GuaFinancialDebtDetailEntity> detailEntityList = new ArrayList<>();
        GuaFinancialDebtDetailEntity entity5 = data.get(5);
        entity5.setProjectName("资产总额");
        detailEntityList.add(entity5);

        GuaFinancialDebtDetailEntity entity6 = data.get(6);
        entity6.setProjectName("    其中：货币资金");
        detailEntityList.add(entity6);

        GuaFinancialDebtDetailEntity entity7 = data.get(7);
        entity7.setProjectName("          存出保证金");
        detailEntityList.add(entity7);

        GuaFinancialDebtDetailEntity entity8 = data.get(8);
        entity8.setProjectName("          债权投资");
        detailEntityList.add(entity8);

        GuaFinancialDebtDetailEntity entity9 = data.get(9);
        entity9.setProjectName("          应收代偿款");
        detailEntityList.add(entity9);

        GuaFinancialDebtDetailEntity entity10 = data.get(10);
        entity10.setProjectName("            其中：期限在2年以上（含）的应收代偿款");
        detailEntityList.add(entity10);

        GuaFinancialDebtDetailEntity entity11 = data.get(11);
        entity11.setProjectName("          其他应收款");
        detailEntityList.add(entity11);

        GuaFinancialDebtDetailEntity entity12 = data.get(12);
        entity12.setProjectName("          委托贷款");
        detailEntityList.add(entity12);

        GuaFinancialDebtDetailEntity entity13 = data.get(13);
        entity13.setProjectName("          其他资产");
        detailEntityList.add(entity13);

        GuaFinancialDebtDetailEntity entity14 = data.get(14);
        entity14.setProjectName("负债总额");
        detailEntityList.add(entity14);

        GuaFinancialDebtDetailEntity entity15 = data.get(15);
        entity15.setProjectName("    其中：借款");
        detailEntityList.add(entity15);

        GuaFinancialDebtDetailEntity entity16 = data.get(16);
        entity16.setProjectName("          存入保证金");
        detailEntityList.add(entity16);

        GuaFinancialDebtDetailEntity entity17 = data.get(17);
        entity17.setProjectName("          未到期责任准备金");
        detailEntityList.add(entity17);

        GuaFinancialDebtDetailEntity entity18 = data.get(18);
        entity18.setProjectName("          担保赔偿准备金");
        detailEntityList.add(entity18);

        GuaFinancialDebtDetailEntity entity19 = data.get(19);
        entity19.setProjectName("          其他负债");
        detailEntityList.add(entity19);

        GuaFinancialDebtDetailEntity entity20 = data.get(20);
        entity20.setProjectName("净资产");
        detailEntityList.add(entity20);

        GuaFinancialDebtDetailEntity entity21 = data.get(21);
        entity21.setProjectName("    其中：实收资本");
        detailEntityList.add(entity21);

        GuaFinancialDebtDetailEntity entity22 = data.get(22);
        entity22.setProjectName("          一般风险准备");
        detailEntityList.add(entity22);

        GuaFinancialDebtDetailEntity entity23 = data.get(23);
        entity23.setProjectName("          其他净资产");
        detailEntityList.add(entity23);
        LOGGER.info("插入明细数据");
        guaFinancialDebtDetailDao.insertBatch(detailEntityList);
    }

}
