package com.moinammaoueni.smartHire.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtFilter jwtFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable())

				// JWT = stateless
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				.authorizeHttpRequests(auth -> auth

						// Swagger
						.requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll()

						// public
						.requestMatchers("/api/auth/**").permitAll()
						.requestMatchers("/api/public/jobs/**").permitAll()

						// Candidate
						.requestMatchers("/api/candidate/**").hasRole("CANDIDAT")
						.requestMatchers("/api/candidate/profile/**").hasRole("CANDIDAT")
						
						// Admin
						.requestMatchers("/api/admin/jobs/**").hasRole("ADMIN")	
						
						.requestMatchers("/api/admin/candidates/**").hasRole("ADMIN")
						
						.requestMatchers("/api/admin/applications/**").hasRole("ADMIN")
						
						.requestMatchers("/api/admin/candidates/**").hasRole("ADMIN")
						
						// autres routes
						.anyRequest().authenticated())

				// JWT FILTER ICI
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}