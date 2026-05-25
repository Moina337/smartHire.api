package com.moinammaoueni.smartHire.api.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.moinammaoueni.smartHire.api.entity.Utilisateur;
import com.moinammaoueni.smartHire.api.num.Role;
import com.moinammaoueni.smartHire.api.repository.UtilisateurRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder encoder;

    @Override
    public void run(String... args) {

        if (!utilisateurRepository.existsByEmail("admin@smarthire.com")) {

            Utilisateur admin = Utilisateur.builder()
                    .nom("Admin SmartHire")
                    .email("admin@smarthire.com")
                    .password(encoder.encode("admin123"))
                    .role(Role.ADMIN)
                    .build();

            utilisateurRepository.save(admin);

            System.out.println("Admin créé");
        }
    }
}