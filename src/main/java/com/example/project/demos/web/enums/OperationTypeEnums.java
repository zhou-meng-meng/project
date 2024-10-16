package com.example.project.demos.web.enums;

/**
 * <p>
 * 操作类型枚举
 * </p>
 */
public enum OperationTypeEnums {

    OPERATION_TYPE_QUERY("0","查询"),
    OPERATION_TYPE_ADD("1","新增"),
    OPERATION_TYPE_DELETE("2","删除"),
    OPERATION_TYPE_UPDATE("3","修改"),
    OPERATION_TYPE_IMPORT("4","导入"),
    OPERATION_TYPE_EXPORT("5","导出"),
    OPERATION_TYPE_APPROVE("6","审核"),
    OPERATION_TYPE_CONFIRM("7","确认"),
    OPERATION_TYPE_LOGIN("8","登录"),
    OPERATION_TYPE_UPDATE_PWD("9","修改密码"),
    OPERATION_TYPE_RESET_PWD("10","重置密码"),
    OPERATION_TYPE_CHARGE_OFF("11","冲销"),
    OPERATION_TYPE_INITE_BALANCE("12","初始化账面余额"),
    OPERATION_TYPE_FILE_UPDATE("13","附件修改");

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
