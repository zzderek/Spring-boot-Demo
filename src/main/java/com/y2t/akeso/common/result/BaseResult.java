package com.y2t.akeso.common.result;

import java.io.Serializable;

public class  BaseResult<T> implements Serializable {

    /**
     * 返回信息
     * @mock 操作成功
     */
    private String msg;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 返回码
     * @mock 200
     */
    private String code;


    /**
     * 请求唯一标识
     * @mock 20210505151515516461
     */
    private String  requestId;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "{\"BaseResult\":{"
                + "\"msg\":\""
                + msg + '\"'
                + ",\"data\":"
                + data
                + ",\"code\":"
                + code
                + ",\"requestId\":\""
                + requestId + '\"'
                + "}}";

    }
}
