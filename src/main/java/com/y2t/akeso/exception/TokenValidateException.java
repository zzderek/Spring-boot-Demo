package com.y2t.akeso.exception;

public class TokenValidateException extends  Exception {
    public TokenValidateException() {
        super("Token校验不通过");
    }

    public TokenValidateException(String message) {
        super(message);
    }
}
