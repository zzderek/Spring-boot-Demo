package com.y2t.akeso.controller;

import com.y2t.akeso.annotation.PassToken;
import com.y2t.akeso.common.api.CommonResult;
import com.y2t.akeso.cqe.LoginCommand;
import com.y2t.akeso.cqe.SendMessageCommand;
import com.y2t.akeso.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author ZiTung
 * @description: TODO
 * @date 2020/5/6 15:55
 */
@Api(tags = "用户接口")
@RestController
@RequestMapping("/user")
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private IUserService userService;

    @PassToken
    @ApiOperation(value="发送验证码", notes="根据手机号下发验证码")
    @PostMapping("/sendMessage")
    public CommonResult sendMessage(@RequestBody @Valid SendMessageCommand sendMessageCommand) {
        return userService.generateAuthCode(sendMessageCommand.getPhoneNumber());
    }



    @ApiOperation(value="验证码登陆", notes="根据验证码登陆")
    @PostMapping("/login")
    @PassToken
    public CommonResult login(@RequestBody @Valid LoginCommand command) {
        return userService.login(command.getPhoneNumber(),command.getVcode());
    }

    @ApiOperation(value="测试Token", notes="测试Token")
    @GetMapping("/test")
    public CommonResult tokenTest() {
        return CommonResult.success("Token测试通过");
    }
}
