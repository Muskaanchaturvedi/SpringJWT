package org.example.Services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service
public class TokenService {
    public static final String token_secret="dkjhsvhushhbvgaytgqv";

    //creating token
    public String generateToken(ObjectId userId){
        try{
            Algorithm algorithm=Algorithm.HMAC256(token_secret);
            String token= JWT
                          .create()
                          .withClaim("userId",userId.toString())
                          .withClaim("createdAt", new Date())
                          .sign(algorithm);
            return token;

        }catch(UnsupportedEncodingException | JWTCreationException e){
             e.printStackTrace();
        }
        return null;
    }

    //decoding the secret
    public String getUserIdFromToken(String token){
        try{
            Algorithm algorithm=Algorithm.HMAC256(token_secret);
            JWTVerifier jwtVerifier=JWT.require(algorithm).build();
            DecodedJWT decodedJWT=jwtVerifier.verify(token);
            return decodedJWT.getClaim("userId").asString();

        }catch(UnsupportedEncodingException |JWTCreationException e){
            e.printStackTrace();
        }
        return null;
    }

    //Validating token
    public boolean isValidToken(String token){
        String userId=this.getUserIdFromToken(token);
        return userId!=null;
    }
}
