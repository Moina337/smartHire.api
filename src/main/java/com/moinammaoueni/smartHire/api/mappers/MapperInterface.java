package com.moinammaoueni.smartHire.api.mappers;

import org.mapstruct.Mapper;

import com.moinammaoueni.smartHire.api.dto.JobRequest;
import com.moinammaoueni.smartHire.api.dto.JobResponse;
import com.moinammaoueni.smartHire.api.entity.Job;

@Mapper(componentModel = "spring")
public interface MapperInterface {
	
	Job jobRequestToJob(JobRequest jobRequest);
	
	JobResponse jobToJobResponse(Job job);

}
