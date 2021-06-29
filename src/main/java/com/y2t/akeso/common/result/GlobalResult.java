package com.y2t.akeso.common.result;

import cn.hutool.json.JSONObject;

import java.io.Serializable;
import java.util.Collections;

public class GlobalResult<T> extends BaseResult implements Serializable {
    private static final long serialVersionUID = -7268040542410707954L;

    public GlobalResult() {

    }

    public GlobalResult(String message) {
        this.setMsg(message);
    }


    public GlobalResult(String code, String message) {
        this.setCode(code);
        this.setMsg(message);
    }

    public GlobalResult(String message, T data) {

        this.setMsg(message);
        this.setData(data);
    }

    public static GlobalResult ok() {
        return ok(ResultCodeEnum.SUCCESS);
    }

    public static <T> GlobalResult<T> ok(ResultCodeEnum codeEnum) {
        return baseCreate(codeEnum.getCode(), codeEnum.getMsg());
    }

    public static <T> GlobalResult<T> ok(String message ) {
        GlobalResult objectGlobalResult = baseCreate(ResultCodeEnum.SUCCESS.getCode(), message);
        objectGlobalResult.setResult(new JSONObject());
        return objectGlobalResult;
    }

    public static GlobalResult fail() {
        return fail(ResultCodeEnum.SERVER_ERROR);
    }

    public static GlobalResult fail(String message) {
        return fail(ResultCodeEnum.SERVER_ERROR.getCode(),message);
    }

    public static GlobalResult fail(ResultCodeEnum message) {
        return fail(message.getCode(), message.getMsg());
    }

    public static GlobalResult paramErrorFail(String message) {
        return fail(ResultCodeEnum.PARAM_ERROR.getCode(), message);
    }

    public static GlobalResult fail(String code, String msg) {
        return baseCreate(code, msg);
    }

    private static <T> GlobalResult<T> baseCreate(String code, String msg) {
        GlobalResult result = new GlobalResult();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public GlobalResult<T> setResult(T data) {
        this.setData(data);
        return this;
    }

    public GlobalResult<T> setDataAsEmptyObject() {
        this.setData(new JSONObject());
        return this;
    }

    public GlobalResult<T> setDataAsEmptyList() {
        this.setData(Collections.emptyList());
        return this;
    }
    @Override
    public T getData() {
        return (T) super.getData();
    }


}
