package com.example.project.demos.web.enums;

/**
 * <p>
 * 系统使用枚举
 * </p>
 *
 * @author yinxc
 * @since 2022-3-22
 */
public enum SysEnums {


    SYS_YES_FLAG("Y","是"),
    SYS_NO_FLAG("N","否"),
    ;

    private String code;
    private String desc;

    SysEnums(String code, String desc) {
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
