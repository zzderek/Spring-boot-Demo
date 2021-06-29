package com.y2t.akeso.service;


import com.y2t.akeso.common.CommonException;
import com.y2t.akeso.entity.User;
import com.y2t.akeso.model.LoginResponse;

/**
 * @author ZiTung
 * @date 2020/4/2 14:54
 */
public interface IUserService {

    /**
     *  发送验证码
     * @param telephone 手机号码
     * @return
     */
    void generateAuthCode(String telephone);

    /**
     *  登陆，登陆成功返回Token
     * @param phone 手机号码
     * @param validCode  验证码
     * @return 封装好的返回对象
     */
    LoginResponse login(String phone, String validCode) throws CommonException;

    /**
     *  根据手机号码查询是否存在用户
     * @param phoneNumber 手机号码
     * @return 封装好的返回对象
     */
    User getByPhoneNumber(String phoneNumber);

    /**
     *  根据userId查询是否存在用户
     * @param userId 手机号码
     * @return 封装好的返回对象
     */
    User getByUserId(String userId);
    /**
     * 刷新Token
     * @param token
     * @return 刷新后的Token
     */
    String refreshToken(String token);

    /**
     *
     * 续期token
     * @param userId
     * @param token
     */
    void reNewToken(String userId,String token);
}
