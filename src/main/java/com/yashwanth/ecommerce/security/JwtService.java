package com.yashwanth.ecommerce.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {


    private static final String SECRET_KEY =
            "mysecretkeymysecretkeymysecretkey123456";


    private Key getSigningKey(){

        return Keys.hmacShaKeyFor(
                SECRET_KEY.getBytes(StandardCharsets.UTF_8)
        );

    }



    public String generateToken(String email){

        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 1000 * 60 * 60 * 24
                        )
                )
                .signWith(getSigningKey())
                .compact();

    }




    public String extractUsername(String token){

        return Jwts.parser()
                .verifyWith((javax.crypto.SecretKey)getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();

    }





    public boolean validateToken(String token){


        try{

            Jwts.parser()
                    .verifyWith(
                            (javax.crypto.SecretKey)getSigningKey()
                    )
                    .build()
                    .parseSignedClaims(token);


            return true;


        }
        catch(Exception e){

            return false;

        }

    }





    public boolean isTokenValid(
            String token,
            UserDetails userDetails
    ){

        String username = extractUsername(token);


        return username.equals(
                userDetails.getUsername()
        )
                && validateToken(token);

    }

}