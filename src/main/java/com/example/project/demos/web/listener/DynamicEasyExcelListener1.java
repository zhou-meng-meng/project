package com.example.project.demos.web.listener;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.example.project.demos.web.dto.list.ExcelData1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.*;

public class DynamicEasyExcelListener1 extends AnalysisEventListener<ExcelData1> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicEasyExcelListener1.class);

    private HttpServletRequest request;

    /**
     * 表头数据（存储所有的表头数据）
     */
    private List<Map<Integer, String>> headList = new ArrayList<>();

    /**
     * 数据体
     */
    private List<Map<Integer, String>> dataList = new ArrayList<>();


    public DynamicEasyExcelListener1(HttpServletRequest request,Integer headRowNumber) {
        this.request = request;
        this.headRowNumber = headRowNumber;
    }

    /**
     * 最终返回的解析数据list
     */
    private final List<ExcelData1> data = new ArrayList<>();
    /**
     * 解析数据
     * key是sheetName，value是相应sheet的解析数据
     */
    private final Map<String, List<ExcelData1>> dataMap = new HashMap<>();
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
    public void invoke(ExcelData1 excelData1, AnalysisContext context) {
        LOGGER.info("解析到一条数据:{}", JSON.toJSONString(data));
        /*String project = excelData1.getProject();
        String code = excelData1.getCode();
        BigDecimal amount1 = excelData1.getAmount1();
        BigDecimal amount2 = excelData1.getAmount2();
        BigDecimal tollAmount = excelData1.getTollAmount();*/

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
        Iterator<Map.Entry<String, List<ExcelData1>>> iterator = dataMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, List<ExcelData1>> next = iterator.next();
            String sheetName = next.getKey();//sheet的名称
            List<ExcelData1> list = next.getValue();//sheet中的集合数据
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
    private List<ExcelData1> explainMergeData(List<ExcelData1> list, List<CellExtra> mergeList) {
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
    private Object getInitValueFromList(Integer firstRowIndex, Integer firstColumnIndex, List<ExcelData1> list) {
        Object filedValue = null;
        ExcelData1 object = list.get(firstRowIndex);
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
    public void setInitValueToList(Object filedValue, Integer rowIndex, Integer columnIndex, List<ExcelData1> list) {
        ExcelData1 object = list.get(rowIndex);//
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
    }

}
