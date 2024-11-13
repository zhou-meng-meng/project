package com.example.project.demos.web.utils;

import com.alibaba.excel.EasyExcel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ExcelZipUtil {
    public void exportAndZipExcel(List data, String[] headers, String excelName) throws IOException {
        // 使用EasyExcel导出多个Excel文件
        EasyExcel.write(new File(excelName), String.class).sheet(excelName).doWrite(data);
        // 创建ZipOutputStream，用于生成zip包
        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(new File("output.zip")));
        // 添加Excel文件到zip包
        File[] excelFiles = new File(excelName).listFiles();
        for (File file : excelFiles) {
            FileInputStream fis = new FileInputStream(file);
            ZipEntry zipEntry = new ZipEntry(file.getName());
            zipOutputStream.putNextEntry(zipEntry);
            int len;
            byte[] buffer = new byte[1024];
            while ((len = fis.read(buffer)) != -1) {
                zipOutputStream.write(buffer, 0, len);
            }
            zipOutputStream.closeEntry();
            fis.close();
        }
        // 关闭ZipOutputStream
        zipOutputStream.close();
    }
}
