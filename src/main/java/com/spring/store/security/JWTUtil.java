package com.spring.store.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private Long expiration;

	// Dependencia io.jsonwebtoken, método usado para gerar o token
	public String generateToken(String username) {
		return Jwts.builder().setSubject(username) // Nome do usuário definido
				.setExpiration(new Date(System.currentTimeMillis() + expiration)) // Tempo de expiração definido do
																					// token
				.signWith(SignatureAlgorithm.HS512, secret.getBytes()) // Passando o algoritmo para gerar o token
				.compact();
	}

	// Armazena as reivindicações (Usuário e tempo de autenticação)
	public boolean tokenValido(String token) {
		Claims claims = getClaims(token);
		// Conseguiu recuperar os claims do token
		if (claims != null) {
			String username = claims.getSubject();
			Date expirationDate = claims.getExpiration();
			Date now = new Date(System.currentTimeMillis());
			// Verificar se o token está expirado
			if (username != null && expirationDate != null && now.before(expirationDate)) {
				return true;
			}
		}
		return false;
	}

	public String getUsername(String token) {
		Claims claims = getClaims(token);
		if (claims != null) {
			return claims.getSubject();
		}
		return null;
	}

	private Claims getClaims(String token) {
		// Recupera os Claims através de um token
		try {

			return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
		}

		catch (Exception e) {
			return null;
		}
	}
}
