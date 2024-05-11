package com.example.project.demos.web.enums;

/**
 * <p>
 * 审批状态枚举
 * </p>
 */
public enum ApproveStateEnums {

    APPROVE_STATE_UNAUTH("0","待审核"),
    APPROVE_STATE_PASSED("1","审核通过"),
    APPROVE_STATE_REJECTED("2","审核拒绝");

    private String code;
    private String desc;

    ApproveStateEnums(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public static String getDescByCode(String code){
        String desc="";
        ApproveStateEnums  [] approveStateEnums= values();
        for(ApproveStateEnums approveStateEnum : approveStateEnums){
            if(approveStateEnum.getCode().equals(code.trim())){
                desc = approveStateEnum.getDesc();
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
