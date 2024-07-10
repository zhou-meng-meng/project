package com.example.project.demos.web.listener;

import com.alibaba.excel.read.listener.ReadListener;
import com.example.project.demos.web.utils.ExcelResult;

/**
 * Excel 导入监听
 *
 * @author oathstudio
 */
public interface ExcelListener<T> extends ReadListener<T> {

    ExcelResult<T> getExcelResult();

}
