package com.y2t.akeso.service.impl;

import com.y2t.akeso.common.api.CommonResult;
import com.y2t.akeso.common.api.ResultCode;
import com.y2t.akeso.pojo.Message;
import com.y2t.akeso.service.IUserService;
import com.y2t.akeso.utils.JwtTokenUtils;
import com.y2t.akeso.utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.y2t.akeso.common.api.Contants.HASHMAP_DEFAULT_SIZE;


/**
 * @author ZiTung
 */
@Service
public class UserServiceImpl implements IUserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Value("${jwt.expiration}")
    private String expiration;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Autowired
    private MessageServiceImpl messageService;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public CommonResult generateAuthCode(String telephone) {
        //TODO 生成验证码
        String  authCode = "1111";
        //发送短信

        //新增一条记录，表示注册成功
        Message newMessage = new Message();
        newMessage = new Message();
        newMessage.setPhone(telephone);
        newMessage.setValidCode(authCode);
        LocalDateTime nowTime = LocalDateTime.now();
        LocalDateTime expiredTime = nowTime.plusMinutes(5);
        newMessage.setSendTime(nowTime );
        newMessage.setExpiredTime(expiredTime);
        //根据手机查询短信发送记录
        Message message = messageService.getMessageByPhone(telephone);
        if (null != message ) {
           messageService.updateMessage(newMessage);
        }else {
            messageService.addMessage(newMessage);
        }
        Map<String, String> msgMap = new HashMap<>(HASHMAP_DEFAULT_SIZE);
        msgMap.put("validCode", authCode);
        return CommonResult.success(msgMap,"短信发送成功");


    }


    /**
     * 处理登陆
     *
     * @param phone 手机号码
     * @param authcode 验证码
     * @return
     */
    @Override
    public CommonResult login( String phone, String authcode) {
        CommonResult result = null;
        String token = null;
        //把Token存入redis或更新时间
        String cacheToken = (String) redisUtils.get(phone);
        if (!StringUtils.isEmpty(cacheToken)){
            //TODO 更新缓存时间还是重新生成Token
        }
        try {
            Message message = messageService.getMessageByPhone(phone);
            if (null == message) {
                return CommonResult.failed(ResultCode.USER_GET_VCODE_FIRST);
            }else {
                //验证验证码是否有效
                if (!message.getValidCode().equals(authcode)){
                    logger.info( "验证码错误！");
                    return CommonResult.failed(ResultCode.USER_ERROR_VCODE);
                }
                if(LocalDateTime.now().isAfter(message.getExpiredTime())){
                    logger.info( "验证码已过期！");
                    return CommonResult.failed(ResultCode.USER_EXPIRED_VCODE);
                }

            }
            token = jwtTokenUtils.generateToken(phone);
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("登录异常:{}", e.getMessage());
            return CommonResult.failed(ResultCode.USER_LOGIN_FAILED);
        }

        Map<String, Object> tokenMap = new HashMap<>(HASHMAP_DEFAULT_SIZE);
        //返回信息组装
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        tokenMap.put("authState", 1);
        tokenMap.put("deposit", "20");

        result = CommonResult.success(tokenMap);

        if(redisUtils.set(phone,token,Long.valueOf(expiration))){
            logger.info("Token缓存成功！");
        }else {
            logger.info("Token缓存失败！");
            return CommonResult.failed(ResultCode.FAILED);
        }
        return  result;
    }

    @Override
    public String refreshToken(String token) {
        return jwtTokenUtils.refreshToken(token);
    }


}
