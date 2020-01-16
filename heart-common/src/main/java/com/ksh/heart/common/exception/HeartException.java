package com.ksh.heart.common.exception;

import com.ksh.heart.common.enumeration.RetEnum;

/**
 * 封装异常
 */
public class HeartException extends RuntimeException {

    private Integer code;
    private String message;

    public HeartException(RetEnum retEnum) {
        this.code = retEnum.getRet();
        this.message = retEnum.getMsg();
    }

    public HeartException(String msg) {
        super(msg);
        this.message = msg;
    }

    public HeartException(String msg, Throwable e) {
        super(msg, e);
        this.message = msg;
    }

    public HeartException(String msg, int code, Throwable e) {
        super(msg, e);
        this.message = msg;
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
