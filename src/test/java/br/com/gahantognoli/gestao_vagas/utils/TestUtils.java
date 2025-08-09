package br.com.gahantognoli.gestao_vagas.utils;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtils {
  public static String objectToJson(Object object) {
    final ObjectMapper objectMapper = new ObjectMapper();
    try {
      return objectMapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Error converting object to JSON", e);
    }
  }

  public static String generateToken(UUID idCompany, String secret){
    var expiresIn = Instant.now().plus(Duration.ofHours(2));

     Algorithm algorithm = Algorithm.HMAC256(secret);
     var token = JWT.create()
        .withExpiresAt(expiresIn)
        .withIssuer("javagas")
        .withSubject(idCompany.toString())
        .withClaim("roles", Arrays.asList("COMPANY"))
        .sign(algorithm);

    return token;
  }
}
