package com.y2t.akeso.common.api;

/**
 * @author ZiTung
 * @description: 枚举了一些常用API返回信息体
 * @date 2020/4/2 14:54
 */
public enum ResultCode implements IErrorCode {

    FAILED("500", "内部服务器错误"),
    UNAUTHORIZED("401", "暂未登录或token已经过期"),
    FORBIDDEN("403", "没有相关权限"),
    NOT_FOUND("404", "没有相关服务"),
    VALIDATE_FAILED("000000", "参数检验失败"),
    USER_GET_VCODE_FIRST("100001","请先获取验证码"),
    USER_ERROR_VCODE("100002","验证码错误"),
    USER_EXPIRED_VCODE("100003","验证码已过期"),
    USER_LOGIN_FAILED("100004","登陆异常"),
    TOKEN_VALIDATE_FAILED("100005","Token验证失败");

    /**
     * 返回信息
     */
    private String returnMsg;
    /**
     * 错误码
     */
    private String errorCode;


    ResultCode( String errorCode,String returnMsg) {
        this.errorCode = errorCode;
        this.returnMsg = returnMsg;
    }

    @Override
    public String getReturnMessage() {
        return returnMsg;
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }

}
