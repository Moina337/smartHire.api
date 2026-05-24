package com.moinammaoueni.smartHire.api.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUser {

    public UtilisateurDetails getUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication.getPrincipal() == null ||
                authentication.getPrincipal().equals("anonymousUser")) {

            throw new RuntimeException("Utilisateur non authentifié");
        }

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof UtilisateurDetails userDetails)) {
            throw new RuntimeException("Principal invalide");
        }

        return userDetails;
    }

    public Long getId() {
        return getUser().getId();
    }

    public String getEmail() {
        return getUser().getUsername();
    }

    public String getRole() {
        return getUser().getRole();
    }
}