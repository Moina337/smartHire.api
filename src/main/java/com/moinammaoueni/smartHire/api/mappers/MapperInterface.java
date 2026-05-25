package com.moinammaoueni.smartHire.api.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.moinammaoueni.smartHire.api.config.AppProperties;
import com.moinammaoueni.smartHire.api.dto.CandidateRequest;
import com.moinammaoueni.smartHire.api.dto.CandidateResponse;
import com.moinammaoueni.smartHire.api.dto.DetailCandidatResponse;
import com.moinammaoueni.smartHire.api.dto.JobRequest;
import com.moinammaoueni.smartHire.api.dto.JobResponse;
import com.moinammaoueni.smartHire.api.dto.RegisterRequest;
import com.moinammaoueni.smartHire.api.entity.Candidate;
import com.moinammaoueni.smartHire.api.entity.Job;
import com.moinammaoueni.smartHire.api.entity.Utilisateur;

import lombok.RequiredArgsConstructor;

@Mapper(componentModel = "spring")
public interface MapperInterface {
	
	
	Job jobRequestToJob(JobRequest jobRequest);
	
	JobResponse jobToJobResponse(Job job);
	
	// Candidat 
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "utilisateur", ignore = true)
	@Mapping(target = "cvUrl", ignore = true)
	Candidate candidatRquestToCandidat(CandidateRequest request);
	  
	@Mapping(target = "nom",source = "utilisateur.nom")
	CandidateResponse  candidatToCandidateResponse(Candidate candidate);
	
	@Mapping(target = "nom",source = "utilisateur.nom")
	@Mapping(target = "email",source = "utilisateur.email")
	DetailCandidatResponse candidatToDetailCandidat(Candidate candidate);

}
