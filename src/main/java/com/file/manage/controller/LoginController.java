package com.file.manage.controller;

import com.file.manage.dao.bean.UserBaseEntity;
import com.file.manage.entity.ResponseEntity;
import com.file.manage.service.UserEntityService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class LoginController {

    private final UserEntityService userEntityService;

    public LoginController(UserEntityService userEntityService) {
        this.userEntityService = userEntityService;
    }

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public ResponseEntity<UserBaseEntity> login(@RequestBody UserBaseEntity userBaseEntity) {

        return userEntityService.login(userBaseEntity);
    }


    @ApiOperation("用户注册")
    @PostMapping("/register")
    public ResponseEntity<UserBaseEntity> register(@RequestBody UserBaseEntity userBaseEntity) {

        return userEntityService.register(userBaseEntity);
    }
}
