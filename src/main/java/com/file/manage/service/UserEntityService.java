package com.file.manage.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.file.manage.dao.bean.UserBaseEntity;
import com.file.manage.dao.mapper.UserBaseMapper;
import com.file.manage.entity.ResponseEntity;
import com.file.manage.entity.StatusCode;
import com.file.manage.exception.ServerException;
import com.file.manage.utils.Constant;
import com.file.manage.utils.JwtUtl;
import com.file.manage.utils.RedisUtil;
import com.file.manage.vo.LoginResultVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserEntityService {

    private final RedisUtil redisUtil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserBaseMapper userBaseMapper;

    public UserEntityService(UserBaseMapper userBaseMapper, BCryptPasswordEncoder bCryptPasswordEncoder, RedisUtil redisUtil) {
        this.userBaseMapper = userBaseMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.redisUtil = redisUtil;
    }

    @Value("${expire.time}")
    private int expireTime;

    public ResponseEntity<LoginResultVo> login(UserBaseEntity userBaseEntity) {
        QueryWrapper<UserBaseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userBaseEntity.getUsername());
        UserBaseEntity selectOne = userBaseMapper.selectOne(queryWrapper);
        Optional.ofNullable(selectOne).orElseThrow(() -> new ServerException(StatusCode.LOGIN_FAIL));

        if (bCryptPasswordEncoder.matches(userBaseEntity.getPassword(), selectOne.getPassword())) {
            String token = JwtUtl.createTokenWithParam(selectOne.getUsername(), selectOne.getRoleId());
            updateToken(selectOne, token);
            LoginResultVo loginResultVo = LoginResultVo.builder()
                    .roleId(selectOne.getRoleId())
                    .username(selectOne.getUsername())
                    .token(token).build();
            return ResponseEntity.success(loginResultVo);
        } else
            throw new ServerException(StatusCode.USERNAME_PASSWORD_ERROR);
    }

    private void updateToken(UserBaseEntity userBaseEntity, String token) {
        String key = Constant.RedisConstant.TOKEN_PREFIX + userBaseEntity.getUsername() + userBaseEntity.getRoleId();
        if (!redisUtil.exits(key)) {
            redisUtil.set(key, token, expireTime);
        } else {
            Long ttl = redisUtil.ttl(key);
            if (ttl <= 0) {
                redisUtil.set(key, token, expireTime);
            } else {
                redisUtil.update(key, token, expireTime);
            }
        }
    }

    public ResponseEntity<UserBaseEntity> register(UserBaseEntity userBaseEntity) {
        Integer count = userBaseMapper.selectCount(new QueryWrapper<UserBaseEntity>().eq("username", userBaseEntity.getUsername()));
        if (count == 0) {
            userBaseEntity.setPassword(bCryptPasswordEncoder.encode(userBaseEntity.getPassword()));
            userBaseEntity.setCreateTime(LocalDateTime.now());
            userBaseEntity.setStatus(true);
            userBaseMapper.insert(userBaseEntity);
            if (userBaseEntity.getId() < 0) {
                throw new ServerException(StatusCode.REGISTER_FAIL);
            }
            return ResponseEntity.success(userBaseEntity);
        } else {
            throw new ServerException(StatusCode.USERNAME_EXIST);
        }
    }

}
