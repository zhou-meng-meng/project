package com.example.project.demos.web.Response;

import java.io.Serializable;

/**
 * @author GuanChong
 * @Date 2024/5/9 9:47
 * @ClassName
 * @Description
 */
public class ResponseResult<T> implements Serializable {

    public ResponseResult(Boolean status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ResponseResult() {
    }

    private Boolean status;

    private String message;

    private T data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseResult{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data;
    }

    public static class Builder<T> {
        private static final String SUCCESS_MSG = "操作成功";
        private static final String ERROR_MSG = "操作失败";

        private ResponseResult build() {
            ResponseResult responseResult = new ResponseResult();
            return responseResult;
        }

        public static ResponseResult buildOk() {
            ResponseResult responseResult = new ResponseResult();
            responseResult.setMessage(SUCCESS_MSG);
            responseResult.setStatus(true);
            return responseResult;
        }

        public static ResponseResult buildOk(String message) {
            ResponseResult responseResult = new ResponseResult();
            responseResult.setMessage(message);
            responseResult.setStatus(true);

            return responseResult;
        }

        public static <T> ResponseResult buildOk(String message, T data) {
            ResponseResult responseResult = new ResponseResult();
            responseResult.setMessage(message);
            responseResult.setStatus(true);
            responseResult.setData(data);
            return responseResult;
        }

        public static ResponseResult buildError() {
            ResponseResult responseResult = new ResponseResult();
            responseResult.setMessage(ERROR_MSG);
            responseResult.setStatus(false);

            return responseResult;
        }

        public static ResponseResult buildError(String message) {
            ResponseResult responseResult = new ResponseResult();
            responseResult.setMessage(message);
            responseResult.setStatus(false);

            return responseResult;
        }

        public static <T> ResponseResult buildError(String message, T data) {
            ResponseResult responseResult = new ResponseResult();
            responseResult.setMessage(message);
            responseResult.setStatus(false);
            responseResult.setData(data);
            return responseResult;
        }
    }
}
