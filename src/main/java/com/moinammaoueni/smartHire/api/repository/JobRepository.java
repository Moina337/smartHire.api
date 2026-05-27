package com.moinammaoueni.smartHire.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.moinammaoueni.smartHire.api.entity.Job;

public interface JobRepository extends JpaRepository<Job, Long> {
	
	Page<Job>
	findByTitreContainingIgnoreCase(
	        String keyword,
	        Pageable pageable);

}
