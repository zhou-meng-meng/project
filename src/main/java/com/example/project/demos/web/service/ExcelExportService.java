package com.example.project.demos.web.service;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.Map;

public interface ExcelExportService {
    /**
     * 获取模版路径
     * @param type
     */
    InputStream getTemplateStream(String type);

    /**
     * 组装数据调度
     * @param type
     * @param map
     * @param excelWriter
     * @param writeSheet
     * @return
     */
    void packData(String type, Map<String,Object> map, ExcelWriter excelWriter, WriteSheet writeSheet);

    /**
     * 导出excel
     * @param type
     * @param map
     * @param response
     * @param beanName
     */
    void exportExcel(String type, Map<String,Object> map, HttpServletResponse response, String beanName);
}
