package com.example.project.demos.web.enums;

/**
 * <p>
 * 用户所属类型枚举
 * </p>
 */
public enum UserTypeEnums {


    USER_TYPE_COMPANY("0","总公司"),
    USER_TYPE_FACTORY("1","厂区"),
    USER_TYPE_STORE("2","仓库");

    private String code;
    private String desc;

    UserTypeEnums(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public static String getDescByCode(String code){
        String desc="";
        UserTypeEnums[] userTypeEnums= values();
        for(UserTypeEnums userTypeEnum : userTypeEnums){
            if(userTypeEnum.getCode().equals(code.trim())){
                desc = userTypeEnum.getDesc();
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
