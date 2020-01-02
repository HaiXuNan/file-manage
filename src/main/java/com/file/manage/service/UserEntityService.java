package com.file.manage.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.file.manage.dao.bean.UserBaseEntity;
import com.file.manage.dao.mapper.UserBaseMapper;
import com.file.manage.entity.ResponseEntity;
import com.file.manage.entity.StatusCode;
import com.file.manage.exception.ServerException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserEntityService {

    private final UserBaseMapper userBaseMapper;

    public UserEntityService(UserBaseMapper userBaseMapper) {
        this.userBaseMapper = userBaseMapper;
    }

    public ResponseEntity<UserBaseEntity> login(UserBaseEntity userBaseEntity) {
        QueryWrapper<UserBaseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userBaseEntity.getUsername());
        queryWrapper.eq("password", userBaseEntity.getPassword());
        UserBaseEntity selectOne = userBaseMapper.selectOne(queryWrapper);
        Optional.ofNullable(selectOne).orElseThrow(() -> new ServerException(StatusCode.LOGIN_FAIL));
        return ResponseEntity.success(selectOne);
    }

    public ResponseEntity<UserBaseEntity> register(UserBaseEntity userBaseEntity) {

        userBaseEntity.setPassword(userBaseEntity.getPassword());
        userBaseEntity.setCreateTime(LocalDateTime.now());
        userBaseEntity.setStatus(true);
        userBaseMapper.insert(userBaseEntity);
        if (userBaseEntity.getId() > 0) {
            throw new ServerException(StatusCode.REGISTER_FAIL);
        }
        return ResponseEntity.success(userBaseEntity);
    }
}
