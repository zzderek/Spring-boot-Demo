package com.y2t.akeso.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.y2t.akeso.common.api.CommonResult;
import com.y2t.akeso.common.api.Contants;
import com.y2t.akeso.dao.IUserDao;
import com.y2t.akeso.entity.User;
import com.y2t.akeso.model.LoginResponse;
import com.y2t.akeso.service.IUserService;
import com.y2t.akeso.utils.IDUtils;
import com.y2t.akeso.utils.JwtTokenUtils;
import com.y2t.akeso.utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


/**
 * @author ZiTung
 */
@Service
public class UserServiceImpl implements IUserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    //验证码过期时间，单位：秒
    public  static  final  int CODE_EXPIRE_SECOND = 300;
    private static final String CLAIM_KEY_USERID = "USERID";



    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private IUserDao userDao;

    @Override
    public CommonResult generateAuthCode(String telephone) {
        // 验证码长度
        String authCode = IDUtils.getValidCode(4);
        logger.info("生成的短信验证码：{}", authCode);
        //缓存验证码，过期时间，单位：秒 。 5分钟
        boolean result = redisUtils.set(telephone, authCode, CODE_EXPIRE_SECOND);
        //发送短信
        logger.info("---短信发送中---");
        logger.info("---短信发送成功---");

        //返回验证码
        return CommonResult.success("短信发送成功");

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
        // 验证码检验
        String redisVCode = (String) redisUtils.get(phone);
        if (StringUtils.isEmpty(redisVCode)) {
            logger.info("请获取验证码");
            return CommonResult.failed( "请获取验证码");
        }
        if (!redisVCode.equals(authcode)) {
            logger.info("验证码错误");
            return CommonResult.failed( "验证码错误");
        }

        LoginResponse response = createResponse(phone);


        return  CommonResult.success(response);
    }

    @Override
    public User getByPhoneNumber(String phoneNumber) {
        return userDao.selectByPhone(phoneNumber) ;
    }

    @Override
    public User getByUserId(String userId) {
        return userDao.selectByUserId(userId);
    }

    private LoginResponse createResponse(String phone) {
        String userId = IdUtil.objectId();
        User user =  queryOrSave(userId,phone);
        String token = cacheToken(user.getUserId());
        LoginResponse response = new LoginResponse(user,token);
        return response;
    }

    private String cacheToken(String userId) {
        String token = generateToken(userId);

        if(redisUtils.set(userId,token,0)){
            logger.info("Token缓存成功！");
        }else {
            logger.info("Token缓存失败！");
        }
        return token;
    }
    public String generateToken(String userId) {

        DateTime nowDate = new DateTime();
        String token = JWT.create()
                .withClaim(CLAIM_KEY_USERID, userId)
                .sign(Algorithm.HMAC256(Contants.JWT_SECRET));

        return token;
    }


    private User queryOrSave(String userId, String phone) {
        // 根据手机号码查询是否在本系统登录过
        User user = getByPhoneNumber(phone);
        // 第一次登陆，则新增一个用户信息
        if (user == null) {
            // 新增一个用户信息
            User newUser = new User(userId,userId,phone, "1");
            int result = userDao.addUser(newUser);
            if (result > 0) {
                logger.info("用户{}--{}新增成功",userId,userId);
            }
        }
        // 查询并返回
        User queryUser = getByPhoneNumber(phone);
        return queryUser;
    }

    @Override
    public String refreshToken(String token) {
        //return jwtTokenUtils.refreshToken(token);
        return "";
    }

    @Override
    public void reNewToken(String userId,String token) {
        if(redisUtils.set(userId,token,0)){
            logger.info("Token缓存成功！");
        }else {
            logger.info("Token缓存失败！");
        }
        return;
    }

}
