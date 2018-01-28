package com.example.security;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenUtils {
	 private final Logger logger = Logger.getLogger(this.getClass());

	@Value("myXAuthSecret")
	private String secret;

	@Value("86400")
	private Long expiration;

	public String getUsernameFromToken(String token) {
		String username = null;

		try {
			Claims claims = this.getClaimsFromToken(token);
			if (claims != null) {
				username = claims.getSubject();
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return username;
	}

	private Claims getClaimsFromToken(String token) {
		Claims claims = null;
		try {
			claims = Jwts.parser()
			        .setSigningKey(this.secret.getBytes("UTF-8"))
			        .parseClaimsJws(token)
			        .getBody();
		} catch (Exception e) {
			e.getMessage();
		}

		return claims;
	}

	public Date getExpirationDateFromToken(String token) {
		Date expirationDate = null;
		try {
			final Claims claims = this.getClaimsFromToken(token);
			if (claims != null) {
				expirationDate = claims.getExpiration();
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return expirationDate;
	}

	private boolean isTokenExpired(String token) {
		final Date expirationDate = this.getExpirationDateFromToken(token);
		return expirationDate.before(new Date(System.currentTimeMillis()));
	}

	public boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("sub", userDetails.getUsername());
		claims.put("created", new Date(System.currentTimeMillis()));
		claims.put("scopes", userDetails.getAuthorities().stream().map(s -> s.toString()).collect(Collectors.toList()));
		try {
			return Jwts.builder()
					.setClaims(claims)
					.setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
					.signWith(SignatureAlgorithm.HS512, this.secret.getBytes("UTF-8"))
					.compact();
		} catch (UnsupportedEncodingException e) {
			logger.warn(e.getMessage());
	          return Jwts.builder()
	                  .setClaims(claims)
	                  .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
	                  .signWith(SignatureAlgorithm.HS512, this.secret)
	                  .compact();
		}

	}

}
