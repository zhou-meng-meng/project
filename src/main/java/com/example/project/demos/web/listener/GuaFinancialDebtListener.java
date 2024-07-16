package com.example.project.demos.web.listener;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.example.project.demos.web.dao.GuaFinancialDebtDao;
import com.example.project.demos.web.entity.GuaFinancialDebtEntity;
import com.example.project.demos.web.utils.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.*;

public class GuaFinancialDebtListener extends AnalysisEventListener<GuaFinancialDebtEntity> {
    private static final Logger LOGGER = LoggerFactory.getLogger(GuaFinancialDebtListener.class);

    private HttpServletRequest request;

    private final GuaFinancialDebtDao guaFinancialDebtDao;

    private final String fillingMonth;
    private final String fillingOper;

    /**
     * 表头数据（存储所有的表头数据）
     */
    private List<Map<Integer, String>> headList = new ArrayList<>();

    /**
     * 数据体
     */
    private List<Map<Integer, String>> dataList = new ArrayList<>();


    public GuaFinancialDebtListener(HttpServletRequest request, Integer headRowNumber,GuaFinancialDebtDao guaFinancialDebtDao,String fillingMonth,String fillingOper) {
        this.request = request;
        this.headRowNumber = headRowNumber;
        this.guaFinancialDebtDao = SpringUtils.getBean(GuaFinancialDebtDao.class);
        this.fillingMonth = fillingMonth;
        this.fillingOper = fillingOper;
    }
    public GuaFinancialDebtListener(String fillingMonth,String fillingOper) {
        this.guaFinancialDebtDao = SpringUtils.getBean(GuaFinancialDebtDao.class);
        this.fillingMonth = fillingMonth;
        this.fillingOper = fillingOper;
    }
    /**
     * 最终返回的解析数据list
     */
    private final List<GuaFinancialDebtEntity> data = new ArrayList<>();
    /**
     * 解析数据
     * key是sheetName，value是相应sheet的解析数据
     */
    private final Map<String, List<GuaFinancialDebtEntity>> dataMap = new HashMap<>();
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
    public void invoke(GuaFinancialDebtEntity excelData1, AnalysisContext context) {
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
        Iterator<Map.Entry<String, List<GuaFinancialDebtEntity>>> iterator = dataMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, List<GuaFinancialDebtEntity>> next = iterator.next();
            String sheetName = next.getKey();//sheet的名称
            List<GuaFinancialDebtEntity> list = next.getValue();//sheet中的集合数据
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
    private List<GuaFinancialDebtEntity> explainMergeData(List<GuaFinancialDebtEntity> list, List<CellExtra> mergeList) {
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
    private Object getInitValueFromList(Integer firstRowIndex, Integer firstColumnIndex, List<GuaFinancialDebtEntity> list) {
        Object filedValue = null;
        GuaFinancialDebtEntity object = list.get(firstRowIndex);
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
    public void setInitValueToList(Object filedValue, Integer rowIndex, Integer columnIndex, List<GuaFinancialDebtEntity> list) {
        GuaFinancialDebtEntity object = list.get(rowIndex);//
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
        LOGGER.info("先删除原月份的数据！"+fillingMonth);
        guaFinancialDebtDao.deleteByFillingMonth(fillingMonth);

        LOGGER.info("开始准备插入数据！");
        //准备数据
        List<GuaFinancialDebtEntity> list = new ArrayList<>();
        //资产总额  不换行
        GuaFinancialDebtEntity entity = data.get(4);
        entity.setOrderNum(0);
        list.add(entity);

        //其中：货币资金  二级
        entity = data.get(5);
        StringBuffer sb = new StringBuffer();
        sb.append("    ").append(entity.getProjectName());
        entity.setProjectName(sb.toString());
        entity.setOrderNum(1);
        list.add(entity);

        //存出保证金  三级
        entity = data.get(6);
        sb = new StringBuffer();
        sb.append("        ").append(entity.getProjectName());
        entity.setProjectName(sb.toString());
        entity.setOrderNum(2);
        list.add(entity);

        //债权投资  三级
        entity = data.get(7);
        sb = new StringBuffer();
        sb.append("        ").append(entity.getProjectName());
        entity.setProjectName(sb.toString());
        entity.setOrderNum(3);
        list.add(entity);

        //应收代偿款  三级
        entity = data.get(8);
        sb = new StringBuffer();
        sb.append("        ").append(entity.getProjectName());
        entity.setProjectName(sb.toString());
        entity.setOrderNum(4);
        list.add(entity);

        //应其中：期限在2年以上（含）的应收代偿款 四级
        entity = data.get(9);
        sb = new StringBuffer();
        sb.append("            ").append(entity.getProjectName());
        entity.setProjectName(sb.toString());
        entity.setOrderNum(5);
        list.add(entity);

        //其他应收款  三级
        entity = data.get(10);
        sb = new StringBuffer();
        sb.append("        ").append(entity.getProjectName());
        entity.setProjectName(sb.toString());
        entity.setOrderNum(6);
        list.add(entity);

        //委托贷款 三级
        entity = data.get(11);
        sb = new StringBuffer();
        sb.append("        ").append(entity.getProjectName());
        entity.setProjectName(sb.toString());
        entity.setOrderNum(7);
        list.add(entity);

        //其他资产  三级
        entity = data.get(12);
        sb = new StringBuffer();
        sb.append("        ").append(entity.getProjectName());
        entity.setProjectName(sb.toString());
        entity.setOrderNum(8);
        list.add(entity);

        //负债总额 一级
        entity = data.get(13);
        entity.setOrderNum(9);
        list.add(entity);

        //其中：借款 二级
        entity = data.get(14);
        sb = new StringBuffer();
        sb.append("    ").append(entity.getProjectName());
        entity.setProjectName(sb.toString());
        entity.setOrderNum(10);
        list.add(entity);

        //存入保证金 三级
        entity = data.get(15);
        sb = new StringBuffer();
        sb.append("        ").append(entity.getProjectName());
        entity.setProjectName(sb.toString());
        entity.setOrderNum(11);
        list.add(entity);

        //未到期责任准备金 三级
        entity = data.get(16);
        sb = new StringBuffer();
        sb.append("        ").append(entity.getProjectName());
        entity.setProjectName(sb.toString());
        entity.setOrderNum(12);
        list.add(entity);

        //担保赔偿准备金 三级
        entity = data.get(17);
        sb = new StringBuffer();
        sb.append("        ").append(entity.getProjectName());
        entity.setProjectName(sb.toString());
        entity.setOrderNum(13);
        list.add(entity);

        //其他负债 三级
        entity = data.get(18);
        sb = new StringBuffer();
        sb.append("        ").append(entity.getProjectName());
        entity.setProjectName(sb.toString());
        entity.setOrderNum(14);
        list.add(entity);

        //净资产 一级
        entity = data.get(19);
        entity.setOrderNum(15);
        list.add(entity);

        //其中：实收资本  二级
        entity = data.get(20);
        sb = new StringBuffer();
        sb.append("    ").append(entity.getProjectName());
        entity.setProjectName(sb.toString());
        entity.setOrderNum(16);
        list.add(entity);

        //一般风险准备 三级
        entity = data.get(21);
        sb = new StringBuffer();
        sb.append("        ").append(entity.getProjectName());
        entity.setProjectName(sb.toString());
        entity.setOrderNum(17);
        list.add(entity);

        //其他净资产 三级
        entity = data.get(22);
        sb = new StringBuffer();
        sb.append("        ").append(entity.getProjectName());
        entity.setProjectName(sb.toString());
        entity.setOrderNum(18);
        list.add(entity);
        Date date = new Date();
        for(GuaFinancialDebtEntity detailEntity : data){
            detailEntity.setFillingMonth(fillingMonth);
            detailEntity.setFillingOper(fillingOper);
            detailEntity.setFillingTime(date);
        }
        guaFinancialDebtDao.insertBatch(list);
    }

}
