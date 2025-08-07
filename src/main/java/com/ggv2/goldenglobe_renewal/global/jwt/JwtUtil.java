package com.ggv2.goldenglobe_renewal.global.jwt;

import com.ggv2.goldenglobe_renewal.domain.user.userDTO.TokenResponse;
import com.ggv2.goldenglobe_renewal.global.auth.CustomUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtUtil {
  private final SecretKey key;
  private final long accessTokenValidityInSeconds;
  private final long refreshTokenValidityInSeconds;
  private final CustomUserDetailsService customUserDetailsService;

  public JwtUtil(@Value("${jwt.secret}") String secretKey,
                 @Value("${jwt.access-token-validity-in-seconds}") long accessTokenValidity,
                 @Value("${jwt.refresh-token-validity-in-seconds}") long refreshTokenValidity,
                 CustomUserDetailsService customUserDetailsService) {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    this.key = Keys.hmacShaKeyFor(keyBytes);
    this.accessTokenValidityInSeconds = accessTokenValidity * 1000;
    this.refreshTokenValidityInSeconds = refreshTokenValidity * 1000;
    this.customUserDetailsService = customUserDetailsService;
  }

  public TokenResponse generateTokens(Authentication authentication) {
    long now = (new Date()).getTime();
    Date accessTokenExpiresIn = new Date(now + accessTokenValidityInSeconds);
    Date refreshTokenExpiresIn = new Date(now + refreshTokenValidityInSeconds);

    String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

    String accessToken = Jwts.builder()
        .subject(authentication.getName())
        .claim("auth", authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(",")))
        .issuedAt(new Date())
        .expiration(accessTokenExpiresIn)
        .signWith(key)
        .compact();

    String refreshToken = Jwts.builder()
        .expiration(refreshTokenExpiresIn)
        .signWith(key)
        .compact();

    return new TokenResponse(accessToken, refreshToken);
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
      return true;
    } catch (Exception e) {
      log.info("Invalid JWT token: " + e.getMessage());
    }
    return false;
  }

  public Authentication getAuthentication(String accessToken) {
    Claims claims = parseClaims(accessToken);
    UserDetails userDetails = customUserDetailsService.loadUserByUsername(claims.getSubject());
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  private Claims parseClaims(String accessToken) {
    return Jwts.parser()
        .verifyWith(key)
        .build()
        .parseSignedClaims(accessToken)
        .getPayload();
  }
}
