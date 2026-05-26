package com.moinammaoueni.smartHire.api.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.moinammaoueni.smartHire.api.dto.ApplicationResponse;
import com.moinammaoueni.smartHire.api.dto.PostulationResponse;
import com.moinammaoueni.smartHire.api.num.StatutApplication;

public interface ApplicationService {

	ApplicationResponse postuler(
	        Long jobId,
	        MultipartFile cvFile
	);

    List<PostulationResponse> mesPostulations();

    ApplicationResponse detailPostulation(
            Long applicationId
    );
    
    List<PostulationResponse> toutesPostulations();

    ApplicationResponse detail(Long id);

    ApplicationResponse changerStatut(
            Long id,
            StatutApplication statut
    );
}