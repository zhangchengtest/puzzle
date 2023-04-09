package com.elephant.auth.service;

import com.cunw.framework.utils.serialize.JsonUtil;
import com.elephant.common.constants.TokenConstants;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.codec.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * jwt工具类
 *
 * @author hekang
 * @createTime 2017/6/30 8:44
 */
@Slf4j
public class JwtUtils {

    private static final String PAYLOAD = "Data";

    private static final SignatureAlgorithm alg = SignatureAlgorithm.HS256;

    private static final SecretKey key;

    static {
        String stringKey = TokenConstants.JWT_SECRET;
        byte[] encodedKey = Base64.decode(stringKey);
        key = new SecretKeySpec(encodedKey, alg.getJcaName());
    }

    /**
     * 创建jwt
     *
     * @param id
     * @param t
     * @param maxAge
     * @return
     * @throws Exception
     */
    public static <T> String create(String id, T t, long maxAge) {
        String json = JsonUtil.toJson(t);

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setIssuedAt(now)
                .setSubject(json)
                .setIssuer(TokenConstants.JWT_ISS)
                .signWith(key, alg);

        // 设置超时时间
        if (maxAge >= 0) {
            long expMillis = nowMillis + maxAge;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        return builder.compact();
    }

    /**
     * 解密jwt
     *
     * @param jwt
     * @return
     * @throws Exception
     */
    public static <T> T parse(String jwt, Class<T> clazz) throws JwtException, AuthenticationException {
        try {
            String stringKey = TokenConstants.JWT_SECRET;
            byte[] encodedKey = Base64.decode(stringKey);
            SecretKey mykey = new SecretKeySpec(encodedKey, alg.getJcaName());
            // 解析 claims 对象
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(TokenConstants.JWT_SECRET.getBytes())
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();

            if (claims.containsKey(Claims.EXPIRATION) && claims.containsKey(PAYLOAD)) {
                // 解析对象 T
                String json = (String) claims.get(PAYLOAD);

                return JsonUtil.toObject(json, clazz);
            }

            throw new AuthenticationException("token无效");
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtException(null, null, "token已过期");
        } catch (Exception e) {
            throw new AuthenticationException("token无效");
        }
    }

}
