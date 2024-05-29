package com.example.project.demos.web.enums;

/**
 * <p>
 * 审批中心异常码
 * </p>
 *
 * @author yinxc
 * @since 2022-3-22
 */
public enum ErrorCodeEnums {


    SYS_SUCCESS_FLAG("000000","操作成功"),
    SYS_FAIL_FLAG("999999","操作失败"),

    LOGIN_ERROR("000001","用户名或密码输入错误！"),
    PWD_INITE("000002","当前密码为初始密码，请修改！"),
    PWD_OVERDUE("000003","当前密码已过期，请修改！"),
    PWD_INITE_ERROR("000004","输入的原密码错误，请重新输入"),
    TOKEN_IS_INVALID("000005","无效TOKEN"),
    USER_IS_NOT_EXIST("000006","用户不存在"),
    DICT_TYPE_IS_EXIST("000007","输入的字典类型已经存在"),
    DICT_DATA_IS_EXIST("000008","输入的字典值已经存在"),
    DICT_DATA_ISDEFAULT_EXIST("000009","当前字典类型已经存在默认值"),
    FACTORY_CODE_IS_EXIST("000010","输入的厂区编号已经存在"),
    STORE_CODE_IS_EXIST("000011","输入的仓库编号已经存在"),
    AUTH_USER_NOT_EXIST("000012","没有找到对应的审核人员"),
    CONFIRM_USER_NOT_EXIST("000013","没有找到对应的确认人员");


    public static String getDescByCode(String code){
        String desc="";
        ErrorCodeEnums  [] errorCodeEnums= values();
        for(ErrorCodeEnums errorCodeEnum : errorCodeEnums){
            if(errorCodeEnum.getCode().equals(code.trim())){
                desc = errorCodeEnum.getDesc();
            }
        }
        return desc;
    }

    private String code;
    private String desc;

    ErrorCodeEnums(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
