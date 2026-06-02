package com.moinammaoueni.smartHire.api.mappers;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


import com.moinammaoueni.smartHire.api.dto.ApplicationResponse;
import com.moinammaoueni.smartHire.api.dto.CandidateRequest;
import com.moinammaoueni.smartHire.api.dto.CandidateResponse;
import com.moinammaoueni.smartHire.api.dto.DetailCandidatResponse;
import com.moinammaoueni.smartHire.api.dto.JobRequest;
import com.moinammaoueni.smartHire.api.dto.JobResponse;
import com.moinammaoueni.smartHire.api.dto.PostulationResponse;
import com.moinammaoueni.smartHire.api.entity.Application;
import com.moinammaoueni.smartHire.api.entity.Candidate;
import com.moinammaoueni.smartHire.api.entity.Job;



@Mapper(componentModel = "spring")
public interface MapperInterface {

	Job jobRequestToJob(JobRequest jobRequest);

	JobResponse jobToJobResponse(Job job);

	// Candidat
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "utilisateur", ignore = true)
	@Mapping(target = "cvUrl", ignore = true)
	Candidate candidatRquestToCandidat(CandidateRequest request);

	@Mapping(target = "nom", source = "utilisateur.nom")
	CandidateResponse candidatToCandidateResponse(Candidate candidate);

	@Mapping(target = "nom", source = "utilisateur.nom")
	@Mapping(target = "email", source = "utilisateur.email")
	DetailCandidatResponse candidatToDetailCandidat(Candidate candidate);

	// postulation
   @Mapping(target = "jobTitre", source = "job.titre")
   @Mapping(target = "jobId", source = "job.id")
   PostulationResponse applicationToPostulation(Application application);
	
    
	@Mapping(target = "candidateName", source = "candidate.utilisateur.nom")
	@Mapping(target = "candidateEmail", source = "candidate.utilisateur.email")
	@Mapping(target = "jobTitre", source = "job.titre")
	@Mapping(target = "jobId", source = "job.id")
	@Mapping(target = "candidateId", source = "candidate.id")
	@Mapping(target = "cvFileUrl", source = "candidate.cvUrl")
	ApplicationResponse applicationToResponse(Application application);

}
