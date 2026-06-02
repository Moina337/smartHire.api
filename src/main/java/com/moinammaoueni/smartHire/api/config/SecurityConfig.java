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
    private final OAuth2SuccessHandler oauth2SuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
            .cors(cors -> {})
            .csrf(csrf -> csrf.disable())

            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .authorizeHttpRequests(auth -> auth

                // Swagger
                .requestMatchers(
                        "/swagger-ui/**",
                        "/v3/api-docs/**"
                ).permitAll()

                // Auth API
                .requestMatchers(
                        "/api/auth/**",
                        "/api/public/**"
                ).permitAll()

                // OAuth2 Google
                .requestMatchers(
                        "/oauth2/**",
                        "/login/**"
                ).permitAll()

                // Candidate
                .requestMatchers("/api/candidate/**")
                .hasRole("CANDIDAT")

                // Admin
                .requestMatchers("/api/admin/**")
                .hasRole("ADMIN")

                .requestMatchers("/api/files/cv/**")
                .hasAnyRole("CANDIDAT", "ADMIN")

                .anyRequest().authenticated()
            )

            .oauth2Login(oauth -> oauth
                    .successHandler(oauth2SuccessHandler)
            )

            .addFilterBefore(
                    jwtFilter,
                    UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}