package com.example.project.demos.web.constant;

/**
 * 返回状态码
 *
 * @author oathstudio
 */
public interface HttpStatus {
    /**
     * 操作成功
     */
    int SUCCESS = 200;

    /**
     * 对象创建成功
     */
    int CREATED = 201;

    /**
     * 请求已经被接受
     */
    int ACCEPTED = 202;

    /**
     * 操作已经执行成功，但是没有返回数据
     */
    int NO_CONTENT = 204;

    /**
     * 资源已被移除
     */
    int MOVED_PERM = 301;

    /**
     * 重定向
     */
    int SEE_OTHER = 303;

    /**
     * 资源没有被修改
     */
    int NOT_MODIFIED = 304;

    /**
     * 参数列表错误（缺少，格式不匹配）
     */
    int BAD_REQUEST = 400;

    /**
     * 未授权
     */
    int UNAUTHORIZED = 401;

    /**
     * 访问受限，授权过期
     */
    int FORBIDDEN = 403;

    /**
     * 资源，服务未找到
     */
    int NOT_FOUND = 404;

    /**
     * 不允许的http方法
     */
    int BAD_METHOD = 405;

    /**
     * 资源冲突，或者资源被锁
     */
    int CONFLICT = 409;

    /**
     * 不支持的数据，媒体类型
     */
    int UNSUPPORTED_TYPE = 415;

    /**
     * 系统内部错误
     */
    int ERROR = 500;

    /**
     * 接口未实现
     */
    int NOT_IMPLEMENTED = 501;

    /**
     * 系统警告消息
     */
    public static final int WARN = 601;


    public static final int HTTP_OK = 200;
    public static final int HTTP_CREATED = 201;
    public static final int HTTP_ACCEPTED = 202;
    public static final int HTTP_NOT_AUTHORITATIVE = 203;
    public static final int HTTP_NO_CONTENT = 204;
    public static final int HTTP_RESET = 205;
    public static final int HTTP_PARTIAL = 206;
    public static final int HTTP_MULT_CHOICE = 300;
    public static final int HTTP_MOVED_PERM = 301;
    public static final int HTTP_MOVED_TEMP = 302;
    public static final int HTTP_SEE_OTHER = 303;
    public static final int HTTP_NOT_MODIFIED = 304;
    public static final int HTTP_USE_PROXY = 305;
    public static final int HTTP_TEMP_REDIRECT = 307;
    public static final int HTTP_PERMANENT_REDIRECT = 308;
    public static final int HTTP_BAD_REQUEST = 400;
    public static final int HTTP_UNAUTHORIZED = 401;
    public static final int HTTP_PAYMENT_REQUIRED = 402;
    public static final int HTTP_FORBIDDEN = 403;
    public static final int HTTP_NOT_FOUND = 404;
    public static final int HTTP_BAD_METHOD = 405;
    public static final int HTTP_NOT_ACCEPTABLE = 406;
    public static final int HTTP_PROXY_AUTH = 407;
    public static final int HTTP_CLIENT_TIMEOUT = 408;
    public static final int HTTP_CONFLICT = 409;
    public static final int HTTP_GONE = 410;
    public static final int HTTP_LENGTH_REQUIRED = 411;
    public static final int HTTP_PRECON_FAILED = 412;
    public static final int HTTP_ENTITY_TOO_LARGE = 413;
    public static final int HTTP_REQ_TOO_LONG = 414;
    public static final int HTTP_UNSUPPORTED_TYPE = 415;
    public static final int HTTP_INTERNAL_ERROR = 500;
    public static final int HTTP_NOT_IMPLEMENTED = 501;
    public static final int HTTP_BAD_GATEWAY = 502;
    public static final int HTTP_UNAVAILABLE = 503;
    public static final int HTTP_GATEWAY_TIMEOUT = 504;
    public static final int HTTP_VERSION = 505;

    public static boolean isRedirected(int responseCode) {
        return responseCode == 301 || responseCode == 302 || responseCode == 303 || responseCode == 307 || responseCode == 308;
    }


}
