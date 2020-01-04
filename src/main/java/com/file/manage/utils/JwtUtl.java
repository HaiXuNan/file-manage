package com.file.manage.utils;

import com.google.common.collect.Maps;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class JwtUtl {

    private final static Long EXP_TIME = 1000 * 60 * 5L;
    private final static String SECRET = "fileManage";

    // 该方法使用HS256算法和Secret:bankgl生成signKey
    private static Key getKeyInstance() {
        // We will sign our JavaWebToken with our ApiKey secret
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET);
        return new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
    }

    // 使用HS256签名算法和生成的signingKey最终的Token,claims中是有效载荷
    public static String createTokenWithParam(String userName, int roleId) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("userName", userName);
        map.put("roleId", roleId);
        return createTokenWithMap(map);
    }

    public static String createTokenWithMap(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXP_TIME))
                .setSubject(UUID.randomUUID().toString())
                .signWith(SignatureAlgorithm.HS256, getKeyInstance())
                .compact();
    }

    // 解析Token，同时也能验证Token，当验证失败返回null
    public static Map<String, Object> parserJavaWebToken(String jwt) {
        try {
            return Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(jwt).getBody();
        } catch (Exception e) {
            log.error("json web token verify failed");
            return null;
        }
    }

}


