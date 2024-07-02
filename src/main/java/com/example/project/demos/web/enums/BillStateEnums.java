package com.example.project.demos.web.enums;

/**
 * <p>
 * 单据状态枚举
 * </p>
 */
public enum BillStateEnums {

    BILL_STATE_NORMAL("0","正常"),
    BILL_STATE_UNCONFIRM("1","冲销待确认"),
    BILL_STATE_CHARGE_OFF("2","已冲销"),
    BILL_STATE_CONFIRM_REJECT("3","冲销确认拒绝");

    private String code;
    private String desc;

    BillStateEnums(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public static String getDescByCode(String code){
        String desc="";
        BillStateEnums[] billStateEnums= values();
        for(BillStateEnums billStateEnum : billStateEnums){
            if(billStateEnum.getCode().equals(code.trim())){
                desc = billStateEnum.getDesc();
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
