package com.moinammaoueni.smartHire.api.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.moinammaoueni.smartHire.api.dto.ApplicationResponse;
import com.moinammaoueni.smartHire.api.dto.PostulationResponse;
import com.moinammaoueni.smartHire.api.num.StatutApplication;

public interface ApplicationService {

	ApplicationResponse postuler(
	        Long jobId
	);

    List<PostulationResponse> mesPostulations();

	ApplicationResponse detailPostulation(
            Long applicationId
    );
    
    Page<PostulationResponse> toutesPostulations(Pageable pageable);

    ApplicationResponse detail(Long id);

    ApplicationResponse changerStatut(
            Long id,
            StatutApplication statut
    );
}