package com.file.manage.interceptor;

import com.file.manage.annotation.AccessLimit;
import com.file.manage.entity.StatusCode;
import com.file.manage.exception.ServerException;
import com.file.manage.utils.Constant;
import com.file.manage.utils.IpUtil;
import com.file.manage.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;

/**
 * 接口限流拦截器
 */
@Component
public class AccessLimitInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof MethodHandle)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        AccessLimit annotation = method.getAnnotation(AccessLimit.class);
        int max = annotation.max();
        int second = annotation.second();
        String key = Constant.RedisConstant.ACCESS_LIMIT_PREFIX + IpUtil.getIpAddress(request) + request.getRequestURI();

        //校验是否存在
        boolean exits = redisUtil.exits(key);
        if (!exits) {
            redisUtil.set(key, String.valueOf(1), second);
        } else {
            int count = Integer.parseInt(redisUtil.get(key));
            if (count < max) {
                Long ttl = redisUtil.ttl(key);
                if (ttl <= 0) {
                    redisUtil.set(key, String.valueOf(1), second);
                } else {
                    redisUtil.set(key, String.valueOf(++count), second);
                }
            } else {
                throw new ServerException(StatusCode.ACCESS_LIMIT);
            }
        }

        return true;
    }
}
