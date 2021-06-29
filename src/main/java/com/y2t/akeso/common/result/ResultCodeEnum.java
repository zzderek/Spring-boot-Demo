package com.y2t.akeso.common.result;

/**
 * 返回码枚举类
 */
public enum ResultCodeEnum {

    /**
     * 成功
     */
    SUCCESS("200","操作成功"),
   // FAILED(500,"操作失败"),
    SERVER_ERROR("500","服务器错误"),
    PARAM_ERROR("400","参数错误");


    /**
     * 响应状态码
     */
    private String code;
    /**
     * 响应信息
     */
    private String msg;

    ResultCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
