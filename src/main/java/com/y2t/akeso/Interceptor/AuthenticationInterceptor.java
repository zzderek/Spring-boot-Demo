package com.y2t.akeso.Interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.y2t.akeso.annotation.PassToken;
import com.y2t.akeso.entity.User;
import com.y2t.akeso.exception.TokenValidateException;
import com.y2t.akeso.service.impl.UserServiceImpl;
import com.y2t.akeso.utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author Root
 */
public class AuthenticationInterceptor implements HandlerInterceptor {
    public static final String AUTH_HEADER_KEY = "Authorization";
    private static final String SECRET = "odt-secret";
    private static final String CLAIM_KEY_USERID = "USERID";
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private RedisUtils redisUtils;

    private static Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        logger.info("----进入默认拦截器----");
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
            throw new TokenValidateException("无token，请重新登录");
        }
        // 获取 token 中的 user id
        String userId;
        try {
             userId = JWT.decode(token).getClaim(CLAIM_KEY_USERID).asString();
        } catch (JWTDecodeException j) {
            throw new TokenValidateException("Token解析错误");
        }

        User users = userService.getByUserId(userId);
        if (users == null ) {
            throw new TokenValidateException("用户不存在，请重新登录");
        }
        //取出缓存中的token，比较
        String cacheToken = (String) redisUtils.get(userId);
        if (!cacheToken.equals(token)) {
            throw new TokenValidateException("Token错误");
        }
        // 验证 token
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        try {
            verifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new TokenValidateException("Token错误");
        }

        logger.info("用户{}-Token校验通过",userId);
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }
}
