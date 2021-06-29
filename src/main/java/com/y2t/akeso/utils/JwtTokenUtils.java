package com.y2t.akeso.utils;


//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import com.sun.org.apache.xml.internal.security.algorithms.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * JwtToken生成的工具类.
 * @author ZiTung
 */

@Component
public class JwtTokenUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtils.class);
    private static final String CLAIM_KEY_PHONE = "PHONE";
    private static final String CLAIM_KEY_CREATTIME = "CREATTIME";
    /**
     * 签名密钥
     */
    private static final String SECRET = "quic-user-secret";
    @Value("${jwt.expiration}")
    private Long expiration;
    public static final String AUTH_HEADER_KEY = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
//
//
//    /**
//     * 根据负责生成JWT的token
//     */
//    public String generateToken(String phone) {
//        Date nowDate = new Date();
//        //过期时间
//        Date expireDate = new Date(nowDate.getTime() + expiration * 1000 );
//
//        return Jwts.builder()
//                .claim(CLAIM_KEY_PHONE, phone)
//                .claim(CLAIM_KEY_CREATTIME, nowDate)
//                .setExpiration(expireDate)
//                .signWith(SignatureAlgorithm.HS512, SECRET)
//                .compact();
//    }
//
//
//    /**
//     * 解析token
//     */
//    public  static Claims   getClaimsFromToken(String token) {
//        Claims claims = null;
//        try {
//            claims = Jwts.parser()
//                    .setSigningKey(SECRET)
//                    .parseClaimsJws(token)
//                    .getBody();
//        } catch (Exception e) {
//            e.printStackTrace();
//            LOGGER.info("Token格式验证失败:{}",token);
//        }
//        return claims;
//    }
//
//    /**
//     * 判断token是否已经失效
//     */
//    public static boolean isTokenExpired(String token) {
//        Claims claims = getClaimsFromToken(token);
//        Date expiredDate = claims.getExpiration();;
//        return expiredDate.before(new Date());
//    }
//
//    /**
//     * 从token中获取过期时间
//     */
//    private Date getExpiredDateFromToken(String token) {
//        Claims claims = getClaimsFromToken(token);
//        return claims.getExpiration();
//    }
//
//    /**
//     * 判断token是否可以被刷新
//     */
//    public boolean canRefresh(String token) {
//        return !isTokenExpired(token);
//    }
//
//    /**
//     * 刷新token
//     */
//    public String refreshToken(String token) {
//        Claims claims = getClaimsFromToken(token);
//        String identityId = (String)claims.get(CLAIM_KEY_PHONE);
//        return generateToken(identityId);
//    }
}
