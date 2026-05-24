package com.moinammaoueni.smartHire.api.config;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.moinammaoueni.smartHire.api.entity.Utilisateur;


public class UtilisateurDetails implements UserDetails {

    private static final long serialVersionUID = 1L;

    private final Utilisateur utilisateur;

    public UtilisateurDetails(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    //  ROLES / AUTORISATIONS
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if (utilisateur.getRole() == null) {
            return List.of();
        }

        return List.of(
                new SimpleGrantedAuthority("ROLE_" + utilisateur.getRole().name())
        );
    }

    // PASSWORD
    @Override
    public String getPassword() {
        return utilisateur.getPassword();
    }

    //  IDENTIFIANT (email)
    @Override
    public String getUsername() {
        return utilisateur.getEmail();
    }
    
   

    //  COMPTE NON EXPIRÉ
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //  COMPTE NON VERROUILLÉ
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //  CREDENTIALS NON EXPIRÉS
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // COMPTE ACTIF
    @Override
    public boolean isEnabled() {
        // si tu as un champ actif dans Utilisateur → sinon true
        return true;
    }

    
    //  BONUS PRO (UTILS)
   

    public Long getId() {
        return utilisateur.getId();
    }

    public String getEmail() {
        return utilisateur.getEmail();
    }

    public String getRole() {
        return utilisateur.getRole() != null
                ? utilisateur.getRole().name()
                : null;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }
}