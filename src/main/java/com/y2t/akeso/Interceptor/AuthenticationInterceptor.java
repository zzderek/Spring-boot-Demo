package com.y2t.akeso.Interceptor;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.y2t.akeso.annotation.PassToken;
import com.y2t.akeso.common.api.CommonResult;
import com.y2t.akeso.common.api.Contants;
import com.y2t.akeso.entity.User;
import com.y2t.akeso.exception.TokenValidateException;
import com.y2t.akeso.service.impl.UserServiceImpl;
import com.y2t.akeso.utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.UUID;

/**
 * @author Root
 */
public class AuthenticationInterceptor implements HandlerInterceptor {
    public static final String AUTH_HEADER_KEY = "Authorization";
    private static final String CLAIM_KEY_USERID = "USERID";
    private static final String REQUEST_ID = "requestId";
    private static final String START_TIME = "request-start";
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private RedisUtils redisUtils;

    private static Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    /**
     * 在方法被调用前执行。在该方法中可以做类似校验的功能。如果返回true，则继续调用下一个拦截器。
     * 如果返回false，则中断执行，也就是说我们想调用的方法 不会被执行，但是你可以修改response为你想要的响应。
     * @param httpServletRequest
     * @param httpServletResponse
     * @param object
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        logger.info("----进入默认拦截器----");

        //获取请求参数
        Enumeration em = httpServletRequest.getParameterNames();
        JSONObject data = new JSONObject();
        while (em.hasMoreElements()) {
            String name = (String) em.nextElement();
            String value = httpServletRequest.getParameter(name);
            data.put(name,value);
        }
        StringBuilder sb = new StringBuilder();
   //     sb .append("-------------------------------------------------------------\n");
        HandlerMethod h = (HandlerMethod) object;
        sb.append("Controller: ").append(h.getBean().getClass().getName()).append("\n");
        sb.append("Method    : ").append(h.getMethod().getName()).append("\n");
        sb.append("Body    : ").append(data).append("\n");
        sb.append("URI       : ").append(httpServletRequest.getRequestURI()).append("\n");
        sb.append("URL       : ").append(httpServletRequest.getRequestURL()).append("\n");
      //  logger.info("-------------------------------------------------------------\n");
        Long start = System.currentTimeMillis();
        String uuid = UUID.randomUUID().toString();
        MDC.put(REQUEST_ID, uuid);
        httpServletRequest.setAttribute(START_TIME, start);
        logger.info(sb.toString());

        logger.info("开始进行Token校验");

        // 从 http 请求头中取出 token
        String token = httpServletRequest.getHeader(AUTH_HEADER_KEY);
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        //检查是否有@passtoken注解，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        // 执行认证
        if (token == null) {
            logger.info("无token，请重新登录");
            /**
             * 返回方式1：
             */
/*            handleFalseResponse(httpServletResponse,"无token，请重新登录");
            return false;*/
            /**
             * 返回方式2
             */
            throw new TokenValidateException("无token，请重新登录");
        }
        // 获取 token 中的 user id
        String userId;
        try {
            userId = JWT.decode(token).getClaim(CLAIM_KEY_USERID).asString();
        } catch (JWTDecodeException j) {
            logger.info("Token解析错误");
            throw new TokenValidateException("Token解析错误");
        }

        User users = userService.getByUserId(userId);
        if (users == null) {
            throw new TokenValidateException("用户不存在，请重新登录");
        }
        //取出缓存中的token，比较
        String cacheToken = (String) redisUtils.get(userId);
        if (!cacheToken.equals(token)) {
            throw new TokenValidateException("Token错误");
        }
        // 验证 token
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(Contants.JWT_SECRET)).build();
        try {
            verifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new TokenValidateException("Token错误");
        }

        logger.info("用户{}-Token校验通过", userId);
        return true;
    }


    /**
     * 在方法执行后调用
     * @param request
     * @param httpServletResponse
     * @param o
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {
        Long start = (Long) request.getAttribute(START_TIME);
        Long end = System.currentTimeMillis();
        String uuid = MDC.get(REQUEST_ID);
        logger.info("【请求耗时】：{}毫秒",(end - start));
        logger.info("remove requestId ({}) from logger", uuid);
        logger.info("-----------------\n");
        MDC.remove(REQUEST_ID);
    }

    /**
     *   在整个请求处理完毕后进行回调，也就是说视图渲染完毕或者调用方已经拿到响应。
     *
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param e
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {


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

    private void handleFalseResponse(HttpServletResponse response,String message)
            throws Exception {
        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        CommonResult result = CommonResult.failed(message);
        response.getWriter().write(JSONUtil.toJsonStr(result));
        response.getWriter().flush();
    }

}
