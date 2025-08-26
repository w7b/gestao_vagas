package com.smoothy.gestao.vagas.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;

@TestConfiguration
public class TestUtils {

    @Value("${security.test.token.secret}")
    public static String secretAlgorithm;

    public static String objectToJson(Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateToken(UUID idCompany, String secret) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        var expiresIn = Instant.now().plus(Duration.ofHours(2));

        var token = JWT.create().withIssuer("javagas")
        .withSubject(idCompany.toString())
        .withExpiresAt(expiresIn)
        .withClaim("roles", Arrays.asList("COMPANY"))
        .sign(algorithm);
        return token;
    }
}
