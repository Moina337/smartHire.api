package com.moinammaoueni.smartHire.api.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.moinammaoueni.smartHire.api.dto.JobRequest;
import com.moinammaoueni.smartHire.api.dto.JobResponse;
import com.moinammaoueni.smartHire.api.dto.RegisterRequest;
import com.moinammaoueni.smartHire.api.entity.Job;
import com.moinammaoueni.smartHire.api.entity.Utilisateur;

@Mapper(componentModel = "spring")
public interface MapperInterface {
	
	Job jobRequestToJob(JobRequest jobRequest);
	
	JobResponse jobToJobResponse(Job job);
	
	// utilisateur 
	
	@Mapping(target = "role", ignore = true)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = " password", ignore = true)
	Utilisateur regitreRequestToUtilisateur(RegisterRequest request);

}
