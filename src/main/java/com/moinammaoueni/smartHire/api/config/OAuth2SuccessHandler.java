package com.moinammaoueni.smartHire.api.config;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.moinammaoueni.smartHire.api.entity.Utilisateur;
import com.moinammaoueni.smartHire.api.num.Role;
import com.moinammaoueni.smartHire.api.repository.UtilisateurRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler
        implements AuthenticationSuccessHandler {

    private final UtilisateurRepository utilisateurRepository;
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {

        OAuth2User oauthUser =
                (OAuth2User) authentication.getPrincipal();

        String email =
                oauthUser.getAttribute("email");

        String nom =
                oauthUser.getAttribute("name");

        // chercher user
        Utilisateur utilisateur =
                utilisateurRepository
                        .findByEmail(email)
                        .orElseGet(() -> {

                            Utilisateur nouveau =
                                    Utilisateur.builder()
                                            .nom(nom)
                                            .email(email)
                                            .role(Role.CANDIDAT)
                                            .password(null)
                                            .build();

                            return utilisateurRepository
                                    .save(nouveau);
                        });

        // claims JWT
        Map<String, Object> claims =
                Map.of(
                        "role",
                        utilisateur.getRole().name(),
                        "userId",
                        utilisateur.getId()
                );

        String token =
                jwtService.generateToken(
                        claims,
                        utilisateur.getEmail()
                );

        // redirect Angular
        String redirectUrl =
                "http://localhost:4200/oauth2-success?token="
                        + URLEncoder.encode(
                                token,
                                StandardCharsets.UTF_8);

        response.sendRedirect(redirectUrl);
    }
}