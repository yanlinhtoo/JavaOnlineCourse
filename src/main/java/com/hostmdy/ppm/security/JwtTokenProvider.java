package com.hostmdy.ppm.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.hostmdy.ppm.domain.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.MalformedJwtException;


@Component
public class JwtTokenProvider {
	
	private static final Long EXPIRATION_TIME = 300_0000L;//50min
	private static final SecretKey SECRETE_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
	
	public String generateToken(Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		
		Date now = new Date();
		Date expireDate = new Date(now.getTime()+EXPIRATION_TIME);
		
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
			     .signWith(SECRETE_KEY,SignatureAlgorithm.HS512)
			     .compact();
	}
	
	public boolean validateToken(String token) {
		
		try {
			Jwts.parserBuilder().setSigningKey(SECRETE_KEY).build().parseClaimsJws(token);
			return true;
		} catch (MalformedJwtException e) {
			// TODO: handle exception
			System.out.println("Invalid Jwt TOken");
			e.printStackTrace();
		} catch (ExpiredJwtException e) {
			// TODO: handle exception
			System.out.println("Expire Jwt Token");
			e.printStackTrace();
		} catch (UnsupportedJwtException e) {
			// TODO: handle exception
			System.out.println("Unsupported Jwt Token");
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO: handle exception
			System.out.println("Jwt claims string is empty");
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return false;
		
	}
	
	public Long getUserId(String token) {
		Claims claims = Jwts.parserBuilder().setSigningKey(SECRETE_KEY).build().parseClaimsJws(token).getBody();
		String userId = claims.get("id").toString();
		return Long.parseLong(userId);
		
	}

}


