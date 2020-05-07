package com.y2t.akeso.controller;

import com.y2t.akeso.common.api.CommonResult;
import com.y2t.akeso.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
