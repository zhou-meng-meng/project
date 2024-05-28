package com.example.project.demos.web.enums;

/**
 * <p>
 * 审核/确认结果枚举
 * </p>
 */
public enum ApproveConfirmResultEnums {

    APPROVE_CONFIRM_RESULT_AGREE("1","同意"),
    APPROVE_CONFIRM_RESULT_REJECT("2","拒绝");

    private String code;
    private String desc;

    ApproveConfirmResultEnums(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public static String getDescByCode(String code){
        String desc="";
        ApproveConfirmResultEnums[] approveConfirmResultEnums= values();
        for(ApproveConfirmResultEnums approveConfirmResultEnum : approveConfirmResultEnums){
            if(approveConfirmResultEnum.getCode().equals(code.trim())){
                desc = approveConfirmResultEnum.getDesc();
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
