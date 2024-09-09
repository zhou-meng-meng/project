package com.example.project.demos.web.constant;

/**
 * 通用常量信息
 *
 * @author oathstudio
 */
public interface Constants {
    /**
     * UTF-8 字符集
     */
    String UTF8 = "UTF-8";

    /**
     * GBK 字符集
     */
    String GBK = "GBK";

    /**
     * www主域
     */
    String WWW = "www.";

    /**
     * http请求
     */
    String HTTP = "http://";

    /**
     * https请求
     */
    String HTTPS = "https://";

    /**
     * 成功标记
     */
    Integer SUCCESS = 200;

    /**
     * 失败标记
     */
    Integer FAIL = 500;

    /**
     * 登录成功状态
     */
    String LOGIN_SUCCESS_STATUS = "0";

    /**
     * 登录失败状态
     */
    String LOGIN_FAIL_STATUS = "1";

    /**
     * 登录成功
     */
    String LOGIN_SUCCESS = "Success";

    /**
     * 注销
     */
    String LOGOUT = "Logout";

    /**
     * 注册
     */
    String REGISTER = "Register";

    /**
     * 登录失败
     */
    String LOGIN_FAIL = "Error";

    /**
     * 验证码有效期（分钟）
     */
    long CAPTCHA_EXPIRATION = 2;

    /**
     * 防重提交 redis key
     */
    String REPEAT_SUBMIT_KEY = "repeat_submit:";

    /**
     * 初始密码加密
     */
    String INITE_PWD_ENCODE = "e10adc3949ba59abbe56e057f20f883e";

    /**
     * 密码过期天数
     */
    int OVERDUE_PWD_DAYS = 60;

    /**
     * 用户token
     */
    String TOKEN = "token";
    /**
     * 请求类型
     */
    String REQ_TYPE_GET = "GET";
    String REQ_TYPE_POST = "POST";
    String SUCCESS_STR = "success";
    String ERROR_STR = "error";

    String SYSTEM_CODE = "system";

    /**
     * 厂区编号前缀
     */
    String FACTORY_CODE_PREFIX = "F";
    /**
     * 仓库编号前缀
     */
    String STORE_CODE_PREFIX = "S";
    /**
     * 短横线
     */
    String SHORT_TERM_LINE = "-";

    String INITE_PWD = "123456";
    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";
    public static String YYYYMMDD = "yyyyMMdd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

}
