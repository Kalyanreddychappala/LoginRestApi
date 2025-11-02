package com.durga.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtils {
	
	
	private String SECRET_KEY="SECRET";
	
	
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims=new HashMap<>();
		return createToken(claims,userDetails.getUsername());
	}


	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+1000*60*60))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}
	
	public <T> T extractClaims(String token,Function<Claims,T>claimsResolver) {
		final Claims claims=extractAllClaims(token);
		return claimsResolver.apply(claims);
	}


	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}
	
	public String extractUsername(String token) {
		return extractClaims(token, Claims::getSubject);
	}
	
	public Date extractExpiration(String token) {
		return extractClaims(token, Claims::getExpiration);
	}
	
	public boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	public boolean isTokenValidate(String token,UserDetails userDetails) {
		final String username=userDetails.getUsername();
		return (username.equals(userDetails.getUsername())&&!isTokenExpired(token));
	}
	
	
}
