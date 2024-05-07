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

    LOGIN_ERROR("000001","用户名或密码输入错误"),


    /**
     * D4错误码
     */
    INPUT_DATA_ILLEGAL_MA_ERROR("ERMA000001","输入数据非法，请检查"),
    MA_NO_REDIS_KEY_MA_ERROR("ERMA000002","不存在的redis key"),
    AUTH_LEVEL_IN_USE_MA_ERROR("ERMA000003","该授权级别正在被使用，不能删除"),
    NO_AUTH_DEAL_ACCOUNT_MA_ERROR("ERMA000004","授权员没有该交易账号的操作权限"),
    AUTH_GROUP_IN_USE_MA_ERROR("ERMA000005","该授权分组正在被使用，不能删除"),
    NO_POLICY_FIND_MA_ERROR("ERMA000006","没有找到匹配到交易类型和币种的授权模式"),
    AUTH_GROUP_NAME_EXIST_MA_ERROR("ERMA000007","该授权分组名称已经存在"),
    NO_ROLE_ITEM_FIND_MA_ERROR("ERMA000008","找不到角色和功能对应的授权类型和授权形式"),
    USER_CANNOT_AUTH_SELF_ORDER_MA_ERROR("ERMA000009","用户不能对自己提交的交易进行授权"),
    OPER_SATATE_WRONG_MA_ERROR("ERMA000010","操作员状态不正确"),
    AUTHER_ATATE_WRONG_MA_ERROR("ERMA000011","授权员状态不正确"),
    AUTH_PWD_WRONG_MA_ERROR("ERMA000012","授权员输入密码不正确"),
    ORDER_UPDATED_MA_ERROR("ERMA000013","指令已被他人修改，请重新查询修改"),
    CURRENT_AUTH_EXIST_MA_ERROR("ERMA000014","当前记录已存在待授权申请"),
    NO_AUTH_POLICY_SET_MA_ERROR("ERMA000015","未设置授权模式"),
    NO_UPDATE_APPROVAL_MA_ERROR("ERMA000016","不能修改待审批的交易"),
    NO_ORDER_ACCOUNT_AUTU_MA_ERROR("ERMA000017","用户不具有原指令涉及的账号的操作权限"),
    NO_AUTH_OPER_MA_ERROR("ERMA000018","没有授权人"),
    ORDER_NOT_EXIST_MA_ERROR("ERMA000019","指令不存在"),
    FUNCTION_ID_MA_ERROR("ERMA000020","交易码错误"),
    ONLY_UNAUTH_DEAL_MA_ERROR("ERMA000021","只能对待授权的指令作授权处理"),
    ONLY_WAIT_MANUAL_SEND_DEAL_MA_ERROR("ERMA000022","只能对待手工发送的指令进行发送处理"),
    ONLY_UNAPPROVAL_DEAL_MA_ERROR("ERMA000023","只能对待审批指令进行审批处理"),
    ONLY_SAVED_TO_UPDATE_MA_ERROR("ERMA000024","只有已保存的指令能够进行删除操作"),
    ONLY_SAVED_TO_SUBMIT_MA_ERROR("ERMA000025","只有已保存的指令能够进行提交操作"),
    ONLY_CANCEL_DEAL_MA_ERROR("ERMA000026","只有待复核、待授权、待审核、待发送、顺延、预约待处理的指令能够进行取消操作"),
    NO_USERS_TO_REAPPROVAL_MA_ERROR("ERMA000027","没有足够的用户完成复核流程"),
    NO_USERS_TO_AUTH_MA_ERROR("ERMA000028","没有足够的用户完成授权流程"),
    POLICY_IN_USE_MA_ERROR("ERMA000029","当前授权策略正在使用中，不可删除"),
    ONLY_SUBMITER_CAN_DEAL_MA_ERROR("ERMA000030","只允许提交人操作"),
    NO_AUTH_SET_MA_ERROR("ERMA000031","匹配到的授权模式没有授权分组配置"),
    NO_DELETED_UPDATE_MA_ERROR("ERMA000032","不能修改已删除的交易"),
    ORDER_IS_UPDATED_MA_ERROR("ERMA000033","指令已被他人修改，请重新查询处理"),
    ONLY_UNAUTH_TO_CANCEL("ERMA000034","只有预约待处理的指令能够进行取消操作"),
    NO_POLICY_MATCH_MA_ERROR("ERMA000035", "没有匹配到授权策略"),
    AUTH_QUEUE_MA_ERROR("ERMA000036","授权队列错误"),
    ORDER_IS_DEALED_MA_ERROR("ERMA000037","要授权的操作已被处理"),
    AUTH_LEVEL_NAME_EXIST_MA_ERROR("ERMA000038","该授权级别名称已经存在"),
    OPER_HAS_NO_AUTH_MA_ERROR("ERMA000039","授权员没有该交易要求的授权类型"),
    NO_AUTH_BSNDEF_FIND_MA_ERROR("ERMA000040","没有找到该操作的授权业务配置信息"),
    NO_AUTH_BSNDEF_SET_MA_ERROR("ERMA000041","没有找到该操作的授权业务动态调用配置信息"),
    ONLY_WITH_AUTH_CAN_RECALL("ERMA000042","只有待授权交易可以撤回"),
    ORDER_DATA_NOT_COMPLETE("ERMA000043","指令数据不完整"),
    ORDER_DATA_NOT_EXIST("ERMA000044","指令数据不存在"),
    SEND_TO_CSS_AUTHER_MA_ERROR("ERMA000045","推送柜面待授权通知消息失败"),
    SESSION_DATA_EXCEPITON("ERMA000046","会话数据不完整"),
    ORDER_UPDATE_NOT_ALLOW("ERMA000047","当前交易状态不允许修改"),
    POLICY_EXIST_MA_ERROR("ERMA000048","已存在相同配置的授权策略，请检查"),
    HAVE_NO_AUTH("ERMA000049","操作员对交易没有权限"),
    //ALM00026285-关于对公线上营业厅多笔转账优化展示提交顺序等需求的优化  zhouhy  2023-02-28
    FLOW_IS_EDITING("ERMA000050","交易审核已提交至银行，请勿重复提交");

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
