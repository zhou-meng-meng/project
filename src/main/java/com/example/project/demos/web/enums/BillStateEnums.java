package com.example.project.demos.web.enums;

/**
 * <p>
 * 单据状态枚举
 * </p>
 */
public enum BillStateEnums {

    BILL_STATE_NORMAL("0","正常"),
    BILL_STATE_CHARGE_OFF("1","已冲销");

    private String code;
    private String desc;

    BillStateEnums(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public static String getDescByCode(String code){
        String desc="";
        BillStateEnums[] operationTypeEnums= values();
        for(BillStateEnums operationTypeEnum : operationTypeEnums){
            if(operationTypeEnum.getCode().equals(code.trim())){
                desc = operationTypeEnum.getDesc();
            }
        }
        return desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
