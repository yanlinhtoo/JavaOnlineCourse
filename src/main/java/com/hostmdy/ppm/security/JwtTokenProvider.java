package com.hostmdy.ppm.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.hostmdy.ppm.domain.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.MalformedJwtException;


@Component
public class JwtTokenProvider {
	
	private static final Long EXPIRATION_TIME = 30_000L;//30s
	private static final String SECRETE_KEY = "JwtsSecreteKey";
	
	public String generateToken(Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		
		Date now = new Date();
		Date expireDate = new Date(now.getTime() * EXPIRATION_TIME);
		
		String userId = user.getId().toString();
		
		Map<String,Object> claims = new HashMap<>();
		claims.put("id", user.getId());
		claims.put("username", user.getUsername());
		claims.put("fullname", user.getFullname());
		
		return Jwts.builder()
			     .setSubject(userId)
			     .setClaims(claims)
			     .setIssuedAt(now)
			     .setExpiration(expireDate)
			     .signWith(SignatureAlgorithm.HS512,SECRETE_KEY.getBytes())
			     .compact();
	}
	
	public boolean validateToken(String token) {
		
		try {
			Jwts.parser().setSigningKey(SECRETE_KEY).parseClaimsJws(token);
			return true;
		} catch (SignatureException e) {
			// TODO: handle exception
			System.out.println("Invalid Jwt Token");
			
		} catch (MalformedJwtException e) {
			// TODO: handle exception
			System.out.println("Invalid Jwt TOken");
		} catch (ExpiredJwtException e) {
			// TODO: handle exception
			System.out.println("Expire Jwt Token");
		} catch (UnsupportedJwtException e) {
			// TODO: handle exception
			System.out.println("Unsupported Jwt Token");
		} catch (IllegalArgumentException e) {
			// TODO: handle exception
			System.out.println("Jwt claims string is empty");
		}
		
		return false;
		
	}
	
	public Long getUserId(String token) {
		Claims claims = Jwts.parser().setSigningKey(SECRETE_KEY).parseClaimsJws(token).getBody();
		Long userId = (Long) claims.get("id");
		return userId;
		
	}

}


