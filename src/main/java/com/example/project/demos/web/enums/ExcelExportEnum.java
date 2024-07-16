package com.example.project.demos.web.enums;

import org.apache.commons.lang3.StringUtils;

public enum ExcelExportEnum {
    TEMPLATE1("G1","/templates/G1.xlsx","融资担保公司名录（G1）","packData1"),
    TEMPLATE2("G2","/templates/G2.xlsx","融资担保公司及人员情况(G2)","packData2"),
    TEMPLATE3("G3","/templates/G3.xlsx","融资担保公司资产负债情况表（G3）","packData3"),
    TEMPLATE4("G4","/templates/G4.xlsx","融资担保公司利润情况表（G4）","packData4"),
    TEMPLATE5("G5","/templates/G5.xlsx","融资担保公司业务情况表（G5)","packData5"),
    TEMPLATE6("G6","/templates/G4.xlsx","融资担保公司责任余额和资产比例情况(G6)","packData6"),
    TEMPLATE7("G7","/templates/G4.xlsx","融资担保公司月度监管报表(G7)","packData7"),
    TEMPLATE8("G8","/templates/G4.xlsx","政府性融资担保机构月度监管报表(G8)","packData8");
    ExcelExportEnum(String enumVal, String enumName, String enumDesc, String enumExp) {
        this.enumVal  = enumVal;
        this.enumName = enumName;
        this.enumDesc = enumDesc;
        this.enumExp = enumExp;
    }

    public static ExcelExportEnum getEnum(String enumVal){
        if(StringUtils.isBlank(enumVal)){
            return null;
        }
        for (ExcelExportEnum excelExportEnum : ExcelExportEnum.values()) {
            if(StringUtils.isNotBlank(excelExportEnum.getEnumVal())&& excelExportEnum.getEnumVal().equals(enumVal)){
                return excelExportEnum;
            }
        }
        return null;
    }

    private String enumVal;
    private String enumName;
    private String enumDesc;

    private String enumExp;

    public String getEnumVal() {
        return enumVal;
    }

    public void setEnumVal(String enumVal) {
        this.enumVal = enumVal;
    }

    public String getEnumName() {
        return enumName;
    }

    public void setEnumName(String enumName) {
        this.enumName = enumName;
    }

    public String getEnumDesc() {
        return enumDesc;
    }

    public void setEnumDesc(String enumDesc) {
        this.enumDesc = enumDesc;
    }

    public String getEnumExp() {
        return enumExp;
    }

    public void setEnumExp(String enumExp) {
        this.enumExp = enumExp;
    }
}
