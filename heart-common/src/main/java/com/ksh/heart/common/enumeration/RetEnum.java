package com.ksh.heart.common.enumeration;

/**
 * 返回信息枚举
 */
public enum RetEnum {
    /**
     * 成功
      */
    SUCCESS(200,"成功"),
    /**
     * 失败
     */
    ERROR(400,"失败"),
    /**
     * 签名异常
     */
    SIGN_ERROR(401, "签名验证失败"),
    /**
     * 禁止访问
     */
    FORBID(403,"禁止访问"),
    /**
     *  服务器异常
     */
    SERVER_EXCEPTION(500,"未知的服务器异常"),
    /**
     * token过期
     */
    LOGIN_EXPIRED(1000,"token已过期请重新登陆"),
    /**
     * 参数异常
     */
    ERROR_PARAM(1001,"参数异常"),
    /**
     * 渲染界面错误
     */
    WRITE_ERROR(1002, "渲染界面错误");

    /**
     * 返回信息
     */
    RetEnum(int ret, String msg){
        setRet(ret);
        setMsg(msg);
    }

    /**
     * 获取返回值
     */
    public int getRet() {
        return ret;
    }

    /**
     * 设置返回值
     */
    private void setRet(int ret) {
        this.ret = ret;
    }

    /**
     * 获取返回信息
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 设置返回信息
     */
    private void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 返回值
     */  
    private int ret;
    /**
     * 返回信息
     */  
    private String msg;
}
