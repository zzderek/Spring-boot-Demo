package com.y2t.akeso.exception;


import com.alibaba.fastjson.JSONException;
import com.y2t.akeso.common.api.CommonResult;
import com.y2t.akeso.common.api.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * 全局异常处理器
 * @author ZiTung
 * @date 2020/4/2 14:54
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(value= NullPointerException.class)
    @ResponseBody
    public CommonResult nullPointerExceptionHandler(Exception e){
        e.printStackTrace();
        logger.error("空指针异常"+e.getMessage());
        return CommonResult.failed(ResultCode.FAILED);
    }
    /**
     * Token校验异常
     */
    @ExceptionHandler(TokenValidateException.class)
    @ResponseBody
    public CommonResult TokenValidateException(TokenValidateException ex){
        String errorMsg = ex.getMessage();
        ex.printStackTrace();
        logger.error("Token校验异常："+errorMsg);
        return  CommonResult.failed(errorMsg);
    }

    @ExceptionHandler(value= JSONException.class)
    @ResponseBody
    public CommonResult jsonExceptionHandler(Exception e){
        e.printStackTrace();
        logger.error("Json解析异常"+e.getMessage());
        return CommonResult.failed(ResultCode.FAILED);
    }


    /**
     * 用来处理bean validation异常
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public  CommonResult BindException(BindException  ex){
        ex.printStackTrace();
        String errorMsg = ex.getMessage();
        logger.error("参数检验错误-BindException："+errorMsg);
        return  CommonResult.failed( errorMsg);
    }

    /**
     * bean参数校验
     * @param ex
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseBody
    public CommonResult handleConstraintViolationException(ConstraintViolationException ex) {
        ex.printStackTrace();
        String errorMsg = ex.getMessage();
        logger.error("参数检验错误-ConstraintViolationException："+errorMsg);
        return CommonResult.failed(errorMsg);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value= Exception.class)
    @ResponseBody
    public CommonResult  exceptionHandler(Exception e){
        e.printStackTrace();
        logger.error("未知异常"+e.getMessage());
        return CommonResult.failed(ResultCode.FAILED);
    }

    /**
     * 参数效验异常处理器
     *
     * @param e 参数验证异常
     * @return ResponseInfo
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult parameterExceptionHandler(MethodArgumentNotValidException e) {
        // 获取异常信息
        BindingResult exceptions = e.getBindingResult();
        // 判断异常中是否有错误信息，如果存在就使用异常中的消息，否则使用默认消息
        if (exceptions.hasErrors()) {
            List<ObjectError> errors = exceptions.getAllErrors();
            if (!errors.isEmpty()) {
                // 这里列出了全部错误参数，按正常逻辑，只需要第一条错误即可
                FieldError fieldError = (FieldError) errors.get(0);
                String errorMsg = fieldError.getDefaultMessage();
                logger.error("参数检验错误："+errorMsg);
                return new CommonResult(ResultCode.VALIDATE_FAILED.getErrorCode(), errorMsg,null);
            }
        }
        return  CommonResult.failed(ResultCode.VALIDATE_FAILED);
    }

}
