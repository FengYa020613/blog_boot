package com.example.mingchao_boot.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.mingchao_boot.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.crypto.AlgorithmMethod;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
@Component
public class TokenUtil {

    private static final String HMAC_ALGORITHM = "HmacSHA256";
    private static final String SECRET_KEY = "secret_key";
    private static final String JWT_SECRET_KEY = "jwt_token";

    private static final Logger log = LoggerFactory.getLogger(TokenUtil.class);

    //生成token
    public static String createToken(String payload){
        try {
            //创建Hmac-SHA256密钥
            SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(),HMAC_ALGORITHM);
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            mac.init(secretKeySpec);

            //计算签名
            byte[] signatureBytes = mac.doFinal(payload.getBytes());

            //使用base64
            String signature = Base64.getEncoder().encodeToString(signatureBytes);
            //返回token
            return signature;

        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            return null;
        }
    }

    //校验token
    public static boolean verifyToken(String token) throws NoSuchAlgorithmException {
        //提取载荷和签名
        String[] parts = token.split("\\.");
        String payload = parts[0];
        String signature = parts[1];
        try {
            //创建Hmac-SHA256密钥
            SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(),HMAC_ALGORITHM);
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            mac.init(secretKeySpec);
            //计算签名
            byte[] calculateSignatureBytes = mac.doFinal(payload.getBytes());
            String calculateSignature = Base64.getEncoder().encodeToString(calculateSignatureBytes);
            //判断是否一致
            return calculateSignature.equals(signature);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            return false;
        }

    }

    //JwtToken
    private static String createJwtToken(String nickName,String phone){
        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET_KEY);
            String token = JWT.create()
                    .withClaim("userPhone",phone)
                    .withClaim("nickname",nickName)
                    .sign(algorithm);
            log.info("用户{}的token生成成功:{}",nickName,token);
            return token;
        }catch (Exception e){
            e.printStackTrace();
            log.error("用户{}的token生成异常",nickName);
            return null;
        }
    }

    //校验token
    private boolean verifyJwtToken(String token){
        try {
            DecodedJWT jwt = JWT.decode(token);

            log.info("token{}校验成功",token);
            return true;
        }catch (Exception e){
            log.error("token{}获取异常:{}",token,e);
            return false;
        }
    }
}
