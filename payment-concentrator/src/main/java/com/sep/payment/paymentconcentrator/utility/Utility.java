package com.sep.payment.paymentconcentrator.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sep.payment.paymentconcentrator.domain.entity.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.UnsupportedEncodingException;

public class Utility {

    public static String readToken(String token) throws UnsupportedEncodingException {
        Algorithm algorithm = Algorithm.HMAC256(Constants.TOKEN_SECRET);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("client").asString();
    }

    public String getToken(String name) throws UnsupportedEncodingException {
        Algorithm algorithm = Algorithm.HMAC256(Constants.TOKEN_SECRET);
        String token = JWT.create()
                .withClaim("client", name)
                .sign(algorithm);
        return token;
    }

}
