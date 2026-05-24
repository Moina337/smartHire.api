package com.moinammaoueni.smartHire.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moinammaoueni.smartHire.api.entity.Candidate;

public interface CandidatRepository extends JpaRepository<Candidate, Long> {
	
	Optional<Candidate> findByUtilisateurId(Long userId);

}
