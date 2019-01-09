package com.spring.operacoes.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.spring.operacoes.security.JWTAuthenticationFilter;
import com.spring.operacoes.security.JWTUtil;

// https://auth0.com/blog/implementing-jwt-authentication-on-spring-boot/

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private Environment env;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	//Endpoints liberados para acesso.
	public static final String[] PUBLIC_MATCHERS = { "/h2-console/**"};
	
	public static final String[] PUBLIC_MATCHERS_GET = { "/produtos/**", "/categorias/**", "/clientes/**", "/h2-console/**" };

	//Padrão do framework - Todos os caminhos do PUBLIC_MATCHERS serão permitidos.
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();
		}
		//Como não estamos usando sessão, não precisamos usar o CSRF, pois nosso sistema é STATELESS
		http.cors().and().csrf().disable();
		http.authorizeRequests().
		antMatchers(PUBLIC_MATCHERS).permitAll().
		antMatchers(HttpMethod.GET,PUBLIC_MATCHERS_GET).permitAll().
		anyRequest().authenticated();
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	//Sobrescreve para indicar o service
	@Override
	public void configure (AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
		
	}

	//Permitindo acesso por multiplas fontes básico de todos os caminhos.
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
	
	//Criptografar a senha do Cliente
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
