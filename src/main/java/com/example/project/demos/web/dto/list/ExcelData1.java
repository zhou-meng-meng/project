package com.example.project.demos.web.dto.list;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExcelData1 {
    @ExcelProperty(index =0)
    private String project;
    @ExcelProperty(index =1)
    private String code;
    @ExcelProperty(index =2)
    private String amount1;
    @ExcelProperty(index =3)
    private String amount2;
    @ExcelProperty(index =4)
    private String tollAmount;
}
