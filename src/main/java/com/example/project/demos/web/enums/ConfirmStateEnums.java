package com.example.project.demos.web.enums;

/**
 * <p>
 * 确认状态枚举
 * </p>
 */
public enum ConfirmStateEnums {


    CONFIRM_STATE_UNDO("0","待确认"),
    CONFIRM_STATE_DONE("1","已确认");

    private String code;
    private String desc;

    ConfirmStateEnums(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public static String getDescByCode(String code){
        String desc="";
        ConfirmStateEnums  [] confirmStateEnums= values();
        for(ConfirmStateEnums confirmStateEnum : confirmStateEnums){
            if(confirmStateEnum.getCode().equals(code.trim())){
                desc = confirmStateEnum.getDesc();
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
