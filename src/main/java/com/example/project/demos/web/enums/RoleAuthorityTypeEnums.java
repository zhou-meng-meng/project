package com.example.project.demos.web.enums;

/**
 * <p>
 * 角色权限类型枚举
 * </p>
 */
public enum RoleAuthorityTypeEnums {


    ROLE_AUTHORITY_TYPE_AUTH("0","审核权限"),
    ROLE_AUTHORITY_TYPE_PRICE("1","单价权限"),
    ROLE_AUTHORITY_TYPE_CONFIRM("2","确认权限");

    private String code;
    private String desc;

    RoleAuthorityTypeEnums(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public static String getDescByCode(String code){
        String desc="";
        RoleAuthorityTypeEnums[] userTypeEnums= values();
        for(RoleAuthorityTypeEnums userTypeEnum : userTypeEnums){
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
