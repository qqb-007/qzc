package info.batcloud.wxc.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import info.batcloud.wxc.core.config.JwtConfig;
import info.batcloud.wxc.core.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @author lvling
 * Date: 2020/1/16
 * Time: 22:01
 */
@Service
public class JwtServiceImpl implements JwtService {

    @Inject
    private JwtConfig jwtConfig;

    private Algorithm algorithm;

    private Logger logger = LoggerFactory.getLogger(JwtServiceImpl.class);

    @PostConstruct
    public void init() throws UnsupportedEncodingException {
        this.algorithm = Algorithm.HMAC256(jwtConfig.getSalt());
    }

    @Override
    public <T> T decode(String token, Class<T> clazz) {
        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            Claim data = jwt.getClaim("data");
            if (data != null) {
                T t = JSON.parseObject(data.asString(), clazz);
                return t;
            }
        } catch (JWTVerificationException exception){
            //Invalid signature/claims
            logger.error("jwt 验证失败");
        }
        return null;
    }

    @Override
    public String encode(Object data, Date expiresTime) {
        JWTCreator.Builder builder = JWT.create();
        builder.withClaim("data", JSON.toJSONString(data));
        String token = builder.withIssuedAt(new Date()).withExpiresAt(expiresTime).sign(algorithm);
        return token;
    }

}
