package com.y2t.akeso.controller;

import cn.hutool.json.JSONObject;
import com.y2t.akeso.annotation.PassToken;
import com.y2t.akeso.common.CommonException;
import com.y2t.akeso.common.result.GlobalResult;
import com.y2t.akeso.cqe.LoginCommand;
import com.y2t.akeso.cqe.SendMessageCommand;
import com.y2t.akeso.model.LoginResponse;
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
    public GlobalResult sendMessage(@RequestBody @Valid SendMessageCommand sendMessageCommand) {
        userService.generateAuthCode(sendMessageCommand.getPhoneNumber());
        return  GlobalResult.ok("短信发送成功");
    }



    @ApiOperation(value="验证码登陆", notes="根据验证码登陆")
    @PostMapping("/login")
    @PassToken
    public GlobalResult<LoginResponse> login(@RequestBody @Valid LoginCommand command) throws CommonException {
        LoginResponse response =  userService.login(command.getPhoneNumber(),command.getVcode());
        return GlobalResult.ok().setResult(response==null?new JSONObject():response);
    }

    @ApiOperation(value="测试Token", notes="测试Token")
    @GetMapping("/test")
    public GlobalResult tokenTest() {
        return GlobalResult.ok("Token测试通过");
    }
}
