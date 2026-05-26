package com.moinammaoueni.smartHire.api.dto;

import java.time.LocalDateTime;

import com.moinammaoueni.smartHire.api.num.StatutApplication;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostulationResponse {
	
	private Long id;

    private Long jobId;

    private String jobTitre;

    private StatutApplication statut;
    
    private LocalDateTime appliedAt;

}
