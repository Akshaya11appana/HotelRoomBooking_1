
package com.ey.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class JwtService {

  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.expirySeconds}")
  private long expiry;

  @Value("${jwt.issuer}")
  private String issuer;

  /** Generate a signed JWT (HS256) with subject and roles. */
  public String generate(String subject, Collection<String> roles) {
    Instant now = Instant.now();
    return Jwts.builder()
        .subject(subject)
        .issuer(issuer)
        .issuedAt(Date.from(now))
        .expiration(Date.from(now.plusSeconds(expiry)))
        .claim("roles", roles)
        .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)), Jwts.SIG.HS256)
        .compact();
  }

  /** Parse and verify a JWT, returning JWS with Claims. */
  public Jws<Claims> parse(String token) {
    return Jwts.parser()
        .verifyWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
        .build()
        .parseSignedClaims(token);
  }

  /** Extract role strings from a verified JWS. */
  @SuppressWarnings("unchecked")
  public List<String> roles(Jws<Claims> jws) {
    Object r = jws.getPayload().get("roles");
    if (r instanceof List<?> list) {
      return list.stream().map(Object::toString).toList();
    }
    return List.of();
  }
}
