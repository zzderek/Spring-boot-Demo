package com.y2t.akeso.aspect;

import cn.hutool.json.JSONUtil;
import com.y2t.akeso.common.api.CommonResult;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

    @Aspect
    @Component
    public class LogAspect {

        private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

        private static final String START_TIME = "request-start";

        private static final String REQUEST_ID = "requestId";

        public static final String AUTH_HEADER_KEY = "Authorization";
        /**
         * 切入点
         */
        @Pointcut("execution(public * com.y2t.akeso.controller.*Controller.*(..))")
        public void log() {

        }

        /**
         * 前置操作
         *
         * @param point 切入点
         */
        @Before("log()")
        public void beforeLog(JoinPoint point) {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
            Map<String, String[]> parameterMap = request.getParameterMap();
         /*   StringBuffer sb = new StringBuffer();
            String requestId = IdUtil.simpleUUID();
            log.info("【request_id】：{}",requestId);*/
            log.info("-----------------\n");
            StringBuffer sb = new StringBuffer();
            sb.append("\n【request_id】：").append(request.getHeader(REQUEST_ID));
           // sb.append("\n【token】：").append(request.getHeader(AUTH_HEADER_KEY));
            sb.append("\n【请求 URL】：").append(request.getRequestURL());
            sb.append("\n【请求 IP】：").append(getIp(request));
            sb.append("\n【请求类名】：").append(point.getSignature().getDeclaringTypeName());
            sb.append("【请求方法名】：").append(point.getSignature().getName());
            sb.append("\n【body】：").append(JSONUtil.toJsonStr(point.getArgs()));
            sb.append("\n【请求参数】：").append(JSONUtil.toJsonStr(parameterMap));
            log.info(sb.toString());
            /*log.info("【请求 URL】：{}",request.getRequestURL());
            log.info("【requestId】: {}",request.getHeader("request_id"));
            log.info("【请求 IP】：{}",getIp(request));
            log.info("【请求类名】：{}",point.getSignature().getDeclaringTypeName());
            log.info("【请求方法名】：{}",point.getSignature().getName());
            log.info("【body】：{}",JSONUtil.toJsonStr(point.getArgs()));
            log.info("【请求参数】：{}",JSONUtil.toJsonStr(parameterMap));*/

            Long start = System.currentTimeMillis();
            request.setAttribute(START_TIME, start);
    //        request.setAttribute(REQUEST_ID, request.getHeader("request_id"));
        }

        /**
         * 环绕操作
         *
         * @param point 切入点
         * @return 原方法返回值
         * @throws Throwable 异常信息
         */
        @Around("log()")
        public Object aroundLog(ProceedingJoinPoint point) throws Throwable {
            Object result = point.proceed();
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
            String requestId =request.getHeader(REQUEST_ID);
            Object finalResult = result;
           /* if (result instanceof AjaxResult) {
                AjaxResult ajaxResult = (AjaxResult) result;
                ajaxResult.put(REQUEST_ID,requestId==null?"":requestId);
                finalResult =  ajaxResult;
            }*/

            if (result instanceof CommonResult) {
                CommonResult commonResult = (CommonResult) result;
                // TODO 请求id
              //  commonResult.setRequestId(requestId==null?"":requestId);
                finalResult =  commonResult;
            }
            log.info("【requestId】：" + requestId + "【返回值】：{}", JSONUtil.toJsonStr(finalResult));
            return finalResult;

        }

        /**
         * 后置操作
         */
        @AfterReturning("log()")
        public void afterReturning() {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
            String requestId =  request.getHeader(REQUEST_ID);
            log.info("【requestId】：{}",requestId);
            Long start = (Long) request.getAttribute(START_TIME);
            Long end = System.currentTimeMillis();
            log.info("【请求耗时】：{}毫秒",(end - start));
            log.info("-----------------\n");

        }

        private String getIp(HttpServletRequest request) {
            String ip = request.getHeader("x-forwarded-for");
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
            return ip;
        }

    }
