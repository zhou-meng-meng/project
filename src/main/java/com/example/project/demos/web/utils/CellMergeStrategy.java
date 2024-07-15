package com.example.project.demos.web.utils;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.merge.AbstractMergeStrategy;
import com.example.project.demos.web.annotation.CellMerge;
import com.example.project.demos.web.dto.materialInventory.ExportDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 列值重复合并策略
 *
 * @author oathstudio
 */
@AllArgsConstructor
@Slf4j
public class CellMergeStrategy extends AbstractMergeStrategy {

    private List<?> list;
    private boolean hasTitle;

    @Override
    protected void merge(Sheet sheet, Cell cell, Head head, Integer relativeRowIndex) {
        List<CellRangeAddress> cellList = handle(list, hasTitle);
        // judge the list is not null
        if (CollectionUtils.isNotEmpty(cellList)) {
            // the judge is necessary
            if (cell.getRowIndex() == 1 && cell.getColumnIndex() == 0) {
                for (CellRangeAddress item : cellList) {
                    sheet.addMergedRegion(item);
                }
            }
        }
    }

    @SneakyThrows
    private static List<CellRangeAddress> handle(List<?> list, boolean hasTitle) {
        List<CellRangeAddress> cellList = new ArrayList<>();
        if (CollectionUtils.isEmpty(list)) {
            return cellList;
        }
        Class<?> clazz = list.get(0).getClass();
        Field[] fields = clazz.getDeclaredFields();
        // 有注解的字段
        List<Field> mergeFields = new ArrayList<>();
        List<Integer> mergeFieldsIndex = new ArrayList<>();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            if (field.isAnnotationPresent(CellMerge.class)) {
                CellMerge cm = field.getAnnotation(CellMerge.class);
                mergeFields.add(field);
                mergeFieldsIndex.add(cm.index() == -1 ? (i - 1) : cm.index());
            }
        }
        // 行合并开始下标
        int rowIndex = hasTitle ? 1 : 0;
        Map<Field, RepeatCell> map = new HashMap<>();
        //一条的map
        Map<Integer, String> rowMap = new HashMap<>();
        //key 0+1+2+3 value line lists
        Map<String, List<Integer>> mergeIndexMap = new HashMap<>();
        //合并索引
        int startIndex = 1;
        //根据导出类进行指定业务类型判断
        if (ExportDTO.class == clazz) {
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < mergeFields.size(); j++) {
                    Field field = mergeFields.get(j);
                    String name = field.getName();
                    String methodName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
                    Method readMethod = clazz.getMethod(methodName);
                    Object val = readMethod.invoke(list.get(i));

                    if (j == 0 || j == 1 || j == 2 || j == 3) {
                        if (StringUtils.isEmpty(rowMap.get(i))) {
                            rowMap.put(i, val == null ? "" : val.toString());
                        } else {
                            rowMap.put(i, rowMap.get(i) + (val == null ? "" : val.toString()));
                        }
                    }
                }
            }
            for (Map.Entry<Integer, String> integerStringEntry : rowMap.entrySet()) {
                List<Integer> indexs;
                if (mergeIndexMap.get(integerStringEntry.getValue()) == null || CollectionUtils.isEmpty(mergeIndexMap.get(integerStringEntry.getValue()))) {
                    indexs = new ArrayList<>();
                } else {
                    indexs = mergeIndexMap.get(integerStringEntry.getValue());
                }
                indexs.add(integerStringEntry.getKey());
                mergeIndexMap.put(integerStringEntry.getValue(), indexs);
            }
            //构造rowMap 获取合并单元格的部分
            // 生成两两合并单元格
            for (Map.Entry<String, List<Integer>> stringListEntry : mergeIndexMap.entrySet()) {
                List<Integer> indexs = stringListEntry.getValue();
                if (indexs.size() == 1) {
                    continue;
                }
                IntSummaryStatistics stats = indexs.stream().mapToInt((x) -> x).summaryStatistics();
                for (int j = 0; j < mergeFields.size(); j++) {
                    int colNum = mergeFieldsIndex.get(j);
                    cellList.add(new CellRangeAddress(stats.getMin() + rowIndex, stats.getMax() + rowIndex, colNum, colNum));
                }
            }
            return cellList;
        }


        // 生成两两合并单元格
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < mergeFields.size(); j++) {
                Field field = mergeFields.get(j);
                String name = field.getName();
                String methodName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
                Method readMethod = clazz.getMethod(methodName);
                Object val = readMethod.invoke(list.get(i));

                int colNum = mergeFieldsIndex.get(j);
                if (!map.containsKey(field)) {
                    map.put(field, new RepeatCell(val, i));
                } else {
                    RepeatCell repeatCell = map.get(field);
                    Object cellValue = repeatCell.getValue();
                    if (cellValue == null || "".equals(cellValue)) {
                        // 空值跳过不合并
                        continue;
                    }
                    if (!cellValue.equals(val)) {
                        if (i - repeatCell.getCurrent() > 1) {
                            cellList.add(new CellRangeAddress(repeatCell.getCurrent() + rowIndex, i + rowIndex - 1, colNum, colNum));
                        }
                        map.put(field, new RepeatCell(val, i));
                    } else if (i == list.size() - 1) {
                        if (i > repeatCell.getCurrent()) {
                            cellList.add(new CellRangeAddress(repeatCell.getCurrent() + rowIndex, i + rowIndex, colNum, colNum));
                        }
                    }
                }
            }
        }
        return cellList;
    }

    @Data
    @AllArgsConstructor
    static class RepeatCell {

        private Object value;

        private int current;

    }
}
