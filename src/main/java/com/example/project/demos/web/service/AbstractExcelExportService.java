package com.example.project.demos.web.service;

import cn.hutool.core.io.IoUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.example.project.demos.web.enums.ExcelExportEnum;
import com.example.project.demos.web.utils.SpringUtils;
import com.example.project.demos.web.utils.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Map;

public abstract class AbstractExcelExportService {

    /**
     * 导出数据
     * @param response
     * @param type
     * @param map
     * @param beanName
     */
    protected void exportData(String type, Map<String,Object> map, HttpServletResponse response, String beanName){
        if(StringUtils.isBlank(beanName)){
            beanName = "excelExportService";
        }
        String fileName = "templates";
        try {
            fileName = URLEncoder.encode(ExcelExportEnum.getEnum(type).getEnumDesc(), "UTF-8").replaceAll("\\+", "%20")+".xlsx";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition","attachment;filename=\""+ fileName +"\"");
        ExcelExportService excelExportService = SpringUtils.getBean(beanName);
        InputStream in = excelExportService.getTemplateStream(type);
        OutputStream out = null;
        BufferedOutputStream bos = null;
        try {
            out = response.getOutputStream();
            bos = new BufferedOutputStream(out);
            ExcelWriter excelWriter = EasyExcel.write(bos).withTemplate(in).build();
            WriteSheet writeSheet = EasyExcel.writerSheet().build();
            fillData(excelWriter,writeSheet,type,map);
            excelWriter.finish();
            bos.flush();
            out.flush();
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            IoUtil.close(in);
            IoUtil.close(out);
            IoUtil.close(bos);
        }
    }

    /**
     * 填充数据
     * @param excelWriter
     * @param writeSheet
     * @param map
     */
    protected abstract void fillData(ExcelWriter excelWriter,WriteSheet writeSheet,String type,Map<String,Object> map);
}
