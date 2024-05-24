package com.example.project.demos.web.enums;

/**
 * <p>
 * 操作类型枚举
 * </p>
 */
public enum OperationTypeEnums {

    OPERATION_TYPE_QUERY("0","查询"),
    USER_TYPE_ADD("1","新增"),
    USER_TYPE_DELETE("2","删除"),
    USER_TYPE_UPDATE("3","修改"),
    USER_TYPE_IMPORT("4","导入"),
    USER_TYPE_EXPORT("5","导出"),
    USER_TYPE_AUTH("6","审核"),
    USER_TYPE_CONFIRM("7","确认");

    private String code;
    private String desc;

    OperationTypeEnums(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public static String getDescByCode(String code){
        String desc="";
        OperationTypeEnums[] operationTypeEnums= values();
        for(OperationTypeEnums operationTypeEnum : operationTypeEnums){
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
