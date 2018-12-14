package com.sep.authservice.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sep.authservice.entity.Constants;

import java.io.UnsupportedEncodingException;

public class Utility {

    public static String readToken(String token) throws UnsupportedEncodingException {
        Algorithm algorithm = Algorithm.HMAC256(Constants.TOKEN_SECRET);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("client").asString();
    }
}
