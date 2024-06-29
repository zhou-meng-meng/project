package com.example.project.demos.web.enums;

/**
 * <p>
 * 菜单类型枚举
 * </p>
 */
public enum FunctionTypeEnums {
    APPROVE_OPERATION_QUEUE("010001","待审核事项"),
    CONFIRM_OPERATION_QUEUE("010002","待确认事项"),
    APPROVE_OPERATION_FLOW("010003","审核流水"),
    CONFIRM_OPERATION_FLOW("010004","确认流水"),
    RAW_MATERIAL_INCOME("020001","来料入库"),
    PRODUCTION_MATERIAL_INCOME("020002","产量入库"),
    REBUILD_INBOUND("020003","准备重造入库"),
    RAW_MATERIAL_OUTBOUND("020004","原材料出库"),
    SALES_OUTBOUND("020005","销售出库"),
    SALES_OUTBOUND_CHARGE_OFF("02000501","销售出库-冲销"),
    TRANSFER_OUTBOUND("020006","调拨出库"),
    REBUILD_OUTBOUND("020007","重造出库"),
    SUPPLY_RETURN("020008","供货方退回"),
    SALES_RETURN("020009","销售客户退回"),
    MATERIAL_INVENTORY("020010","实时库存"),
    MATERIAL_INFO("020011","物料维护"),
    MATERIAL_DOSAGE("020012","物料用量表"),
    MATERIAL_PACKAGE("020013","物料装袋表"),

    SUPPLY_COUSTOMER_PAY("030001","供货客户往来账"),
    SALES_COUSTOMER_PAY("030002","销售方客户往来账"),
    CUSTOMER_PAY_DETAIL("030003","客户往来账明细"),

    CUSTOMER_SALE("040001","销售客户维护"),
    CUSTOMER_SUPPLY("040002","供货方客户维护"),

    SALERS_ORDER("050001","业务员下单"),
    SALERS_ORDER_CHARGE_OFF("05000101","业务员下单-冲销"),
    SALERS_ORDER_RETURN("050002","业务员下单退回"),
    SYS_USER("900001","用户管理"),
    SYS_ROLE("900002","角色管理"),
    SYS_MENU("900003","菜单管理"),
    SYS_DEPT("900004","部门管理"),
    SYS_DICT_TYPE("900005","字典类型管理"),
    SYS_FACTORY("900006","厂区管理"),
    SYS_STOREHOUSE("900007","仓库管理"),
    SYS_LOG("900008","操作日志管理"),
    SYS_DICT_DATA("900009","字典值管理");

    private String code;
    private String desc;

    FunctionTypeEnums(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public static String getDescByCode(String code){
        String desc="";
        FunctionTypeEnums[] functionTypeEnums= values();
        for(FunctionTypeEnums functionTypeEnum : functionTypeEnums){
            if(functionTypeEnum.getCode().equals(code.trim())){
                desc = functionTypeEnum.getDesc();
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
