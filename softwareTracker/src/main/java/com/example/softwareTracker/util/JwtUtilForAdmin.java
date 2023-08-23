package com.example.softwareTracker.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtilForAdmin {

    @Value("${jwt.admin.secret}")
    private String secret;

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secret);
    }
    public String createToken(String username) {
        return JWT.create().withSubject(username).withExpiresAt(new Date(System.currentTimeMillis() + 100*60*1000)).sign(getAlgorithm());
    }

    private DecodedJWT getDecodedJwt(String token) {
        JWTVerifier verifier = JWT.require(getAlgorithm()).build();
        return verifier.verify(token);
    }

    public String validateToken(String token) {
        DecodedJWT decodedJWT = getDecodedJwt(token);
        return decodedJWT.getSubject();
    }

}
