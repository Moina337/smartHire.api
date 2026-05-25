package com.moinammaoueni.smartHire.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moinammaoueni.smartHire.api.entity.Application;

public interface ApplicationRepository
        extends JpaRepository<Application, Long> {

    boolean existsByCandidateIdAndJobId(
            Long candidateId,
            Long jobId
    );

    List<Application> findByCandidateId(
            Long candidateId
    );

    Optional<Application> findByIdAndCandidateId(
            Long applicationId,
            Long candidateId
    );
}