package com.y2t.akeso.service;


import com.y2t.akeso.common.api.CommonResult;

/**
 * @author ZiTung
 * @date 2020/4/2 14:54
 */
public interface IUserService {

    /**
     *  发送验证码
     * @param telephone 手机号码
     * @return 封装好的返回对象
     */
    CommonResult generateAuthCode(String telephone);

    /**
     *  登陆，登陆成功返回Token
     * @param phone 手机号码
     * @param validCode  验证码
     * @return 封装好的返回对象
     */
    CommonResult login(String phone, String validCode);


    /**
     * 刷新Token
     * @param token
     * @return 刷新后的Token
     */
    String refreshToken(String token);

    /**
     * 用户抢红包
     * @param userId
     * @return
     */
    CommonResult getLottery(String userId,int amount);
}
