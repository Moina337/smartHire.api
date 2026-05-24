package com.moinammaoueni.smartHire.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moinammaoueni.smartHire.api.entity.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
	
    boolean existsByEmail(String email);
	
	Optional<Utilisateur> findByEmail(String email);

}
