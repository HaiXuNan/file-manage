package com.file.manage.controller;

import com.file.manage.annotation.AccessLimit;
import com.file.manage.dao.bean.UserBaseEntity;
import com.file.manage.entity.ResponseEntity;
import com.file.manage.service.UserEntityService;
import com.file.manage.vo.LoginResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Api(tags = "用户管理")
@Controller
public class LoginController {

    private final UserEntityService userEntityService;

    public LoginController(UserEntityService userEntityService) {
        this.userEntityService = userEntityService;
    }

    @ApiOperation("跳转登录")
    @GetMapping("/toLogin")
    public String toLogin() {
        return "login.html";
    }

    @ApiOperation("用户登录")
    @ResponseBody
    @AccessLimit(max = 5, second = 10)
    @PostMapping("/user/login")
    public ResponseEntity<LoginResultVo> login(@RequestBody UserBaseEntity userBaseEntity) {
        return userEntityService.login(userBaseEntity);
    }


    @ApiOperation("用户注册")
    @ResponseBody
    @PostMapping("/user/register")
    public ResponseEntity<UserBaseEntity> register(@RequestBody UserBaseEntity userBaseEntity) {

        return userEntityService.register(userBaseEntity);
    }
}
