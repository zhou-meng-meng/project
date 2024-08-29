package com.example.project.demos.web.utils;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.example.project.demos.web.constant.Constants;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class LocalDateConverter implements Converter<Date> {
    @Override
    public Class supportJavaTypeKey() {
        return LocalDateTime.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return null;
    }

    @Override
    public WriteCellData<?> convertToExcelData(Date value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration)  {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.YYYY_MM_DD);
        return new WriteCellData<>(sdf.format(value));
    }
}
