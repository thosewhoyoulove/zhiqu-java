package web.Utils;

import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.HmacKey;
import web.Entry.UserToken;

import java.security.Key;

public class UserTokenUtils {
    /**
     * 生成token
     * @param userToken
     * @param expire
     * @return
     * @throws Exception
     */
    public static String generateToken(UserToken userToken, int expire) throws Exception {
        JwtClaims claims = new JwtClaims();
        claims.setSubject(userToken.getUsername());
        claims.setClaim("user_id",userToken.getUser_id());
        claims.setClaim("username", userToken.getUsername());
        claims.setClaim("status", userToken.getStatus());
        claims.setClaim("class_name", userToken.getClass_name());
        claims.setExpirationTimeMinutesInTheFuture(expire == 0 ? 60*24 : expire);
        Key key = new HmacKey("swf_token_key".getBytes("UTF-8"));

        JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(claims.toJson());
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA256);
        jws.setKey(key);
        jws.setDoKeyValidation(false); // relaxes the key length requirement

        //签名
        String token = jws.getCompactSerialization();
        return token;
    }

    /**
     * 解析token
     * @param token
     * @return
     * @throws Exception
     */
//    public static UserToken getInfoFromToken(String token) throws Exception {
//
//        if (token == null) {
//            return null;
//        }
//
//        Key key = new HmacKey("swf_token_key".getBytes("UTF-8"));
//
//        JwtConsumer jwtConsumer = new JwtConsumerBuilder()
//                .setRequireExpirationTime()
//                .setAllowedClockSkewInSeconds(30)
//                .setRequireSubject()
//                .setVerificationKey(key)
//                .setRelaxVerificationKeyValidation() // relaxes key length requirement
//                .build();
//
//        JwtClaims processedClaims = jwtConsumer.processToClaims(token);
//
//        return new UserToken(
//                processedClaims.getClaimValue("user_id").toString(),
//                processedClaims.getClaimValue("username").toString(),
//                processedClaims.getClaimValue("status"),
//                processedClaims.getClaimValue("class_name").toString());
//    }


}
