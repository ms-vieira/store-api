package com.spring.operacoes.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component 
public class JWTUtil {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private Long expiration;
	
	//Dependencia io.jsonwebtoken, método usado para gerar o token
	public String generateToken (String username) {
		return Jwts.builder()
				.setSubject(username) //Nome do usuário definido
				.setExpiration(new Date(System.currentTimeMillis() + expiration)) //Tempo de expiração definido do token
				.signWith(SignatureAlgorithm.HS512, secret.getBytes()) //Passando o algoritmo para gerar o token
				.compact();
	}
}
