package com.example.softwareTracker.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtilForMachine {

    @Value("${jwt.machine.secret}")
    private String secret;

    private DecodedJWT getDecodedJwt(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }

    public String validateToken(String token) {
        DecodedJWT decodedJWT = getDecodedJwt(token);
        return decodedJWT.getSubject();
    }
}
