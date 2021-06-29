package com.y2t.akeso.exception;


import com.y2t.akeso.common.CommonException;
import com.y2t.akeso.common.result.GlobalResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLIntegrityConstraintViolationException;
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
    public GlobalResult  nullPointerExceptionHandler(NullPointerException e){
        e.printStackTrace();
        logger.error("空指针异常"+e.getMessage());
        return GlobalResult.fail();
    }
    /**
     * -------- 通用异常处理方法 --------
     **/
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public GlobalResult error(Exception e) {
        logger.error(e.getMessage());
        // TODO 生产环境不建议打印
        String description = e.getCause().getMessage();
        return GlobalResult.fail();
    }


    /**
     * 参数校验异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public GlobalResult error(MethodArgumentNotValidException e) {

        e.printStackTrace();
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
                return  GlobalResult.fail(errorMsg);
            }
        }
        return  GlobalResult.fail("参数检验错误");
    }

    /**
     * sql执行异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    @ResponseBody
    public GlobalResult error(SQLIntegrityConstraintViolationException e) {
        e.printStackTrace();
        logger.error(e.getMessage());
        return GlobalResult.fail();
    }

    /**
     * 关键词重复
     *
     * @param e
     * @return
     */
    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseBody
    public GlobalResult error(DuplicateKeyException e) {
        e.printStackTrace();
        logger.error(e.getMessage());
        return GlobalResult.fail();
    }

    /**
     * -------- 自定义定异常处理方法 --------
     **/
    @ExceptionHandler(CommonException.class)
    @ResponseBody
    public GlobalResult error(CommonException e) {
        logger.error(e.getMessage());
        return GlobalResult.fail();
    }


}
