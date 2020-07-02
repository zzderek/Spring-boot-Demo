package com.y2t.akeso.controller;

import com.y2t.akeso.common.api.CommonResult;
import com.y2t.akeso.pojo.vo.MessageVO;
import com.y2t.akeso.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ZiTung
 * @description: TODO
 * @date 2020/5/6 15:55
 */
@Api(tags = "用户接口")
@RestController("/user")
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private IUserService userService;

    @ApiOperation(value="发送验证码", notes="根据手机号下发验证码")
    @PostMapping("/message/{phone}")
    public CommonResult getUser(@PathVariable String phone) {
        return userService.generateAuthCode(phone);
    }

    @ApiOperation(value="登陆", notes="根据验证码登陆")
    @PostMapping("/login")

    public CommonResult login(@RequestBody  MessageVO messageVO) {
        return userService.login(messageVO.getPhone(),messageVO.getValidCode());
    }

    @ApiOperation(value="抢红包", notes="用户抢红包")
    @GetMapping("/{userId}/lottery/{amount}")
    public CommonResult getLottery(@PathVariable String userId,@PathVariable String amount) {
        //用户领取数量为number的红包

        return userService.getLottery(userId,Integer.valueOf(amount));
    }
}
