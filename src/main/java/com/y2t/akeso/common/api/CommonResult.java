package com.y2t.akeso.common.api;


/**
 * @author ZiTung
 * @description: 通用的返回对象
 * @date 2020/4/2 14:54
 */
public class CommonResult<T> {
    private String returnCode;
    private String errorCode;
    private String resultMsg;
    private T data;

    protected CommonResult() {
    }

    public CommonResult(String returnCode,  String errorCode,String resultMsg,T data) {
        this.returnCode = returnCode;
        this.errorCode = errorCode;
        this.resultMsg = resultMsg;
        this.data = data;
    }
    public CommonResult(String returnCode, String errorCode,String resultMsg) {
        this.returnCode = returnCode;
        this.resultMsg = resultMsg;
        this.errorCode = errorCode;
    }
    public CommonResult(String returnCode, String resultMsg,T data) {
        this.returnCode = returnCode;
        this.resultMsg = resultMsg;
        this.errorCode = "";
        this.data = data;
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<T>(ResultCode.SUCCESS, ResultCode.OK, data);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     * @param  message 提示信息
     */
    public static <T> CommonResult<T> success(T data, String message) {
        return new CommonResult<T>(ResultCode.SUCCESS, message, data);
    }

    /**
     * 失败返回结果
     * @param errorCode 错误码
     */
    public static <T> CommonResult<T> failed(IErrorCode errorCode) {
        return new CommonResult<T>(ResultCode.FAIL, errorCode.getErrorCode(),errorCode.getReturnMessage());
    }

    /**
     * 失败返回结果
     * @param errorCode 错误码
     */
    public static <T> CommonResult<T> failed(String errorCode,String resultMsg) {
        return new CommonResult<T>(ResultCode.FAIL, errorCode, resultMsg);
    }

    /**
     * 失败返回结果
     * @param errorCode 错误码
     */
    public static <T> CommonResult<T> failed(IErrorCode errorCode,T data) {
        return new CommonResult<T>(ResultCode.FAIL, errorCode.getErrorCode(), errorCode.getReturnMessage(), data);
    }



    /**
     * 失败返回结果
     * @param message 提示信息
     */
    public static <T> CommonResult<T> failed(String message) {
        return new CommonResult<T>(ResultCode.FAIL,ResultCode.SERVER_ERROR,message ,null);
    }


    /**
     * 服务器错误：404--失败返回结果
     */
    public static <T> CommonResult<T> serverNotFound() {
        return failed(ResultCode.NOT_FOUND);
    }


    /**
     * 服务器错误：500--失败返回结果
     */
    public static <T> CommonResult<T> serverFailed() {
        return failed(ResultCode.FAILED);
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> CommonResult<T> validateFailed() {
        return failed(ResultCode.VALIDATE_FAILED);
    }


    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
