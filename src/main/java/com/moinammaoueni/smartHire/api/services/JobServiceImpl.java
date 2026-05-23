package com.moinammaoueni.smartHire.api.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.moinammaoueni.smartHire.api.dto.JobRequest;
import com.moinammaoueni.smartHire.api.dto.JobResponse;
import com.moinammaoueni.smartHire.api.entity.Job;
import com.moinammaoueni.smartHire.api.exception.JobNotFoundException;
import com.moinammaoueni.smartHire.api.mappers.MapperInterface;
import com.moinammaoueni.smartHire.api.repository.JobRepository;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final MapperInterface mapperInterface;

    public JobServiceImpl(
            JobRepository jobRepository,
            MapperInterface mapperInterface) {

        this.jobRepository = jobRepository;
        this.mapperInterface = mapperInterface;
    }

    @Override
    public JobResponse creerUnJob(JobRequest jobRequest) {

        Job job = mapperInterface.jobRequestToJob(jobRequest);

        Job savedJob = jobRepository.save(job);

        return mapperInterface.jobToJobResponse(savedJob);
    }

    @Override
    public List<JobResponse> listeJob() {

        List<Job> jobs = jobRepository.findAll();

        return jobs.stream()
                .map(mapperInterface::jobToJobResponse)
                .collect(Collectors.toList());
    }

	@Override
	public JobResponse afficheJobParId(long job_id) {
		// TODO Auto-generated method stub
		
		Job job = jobRepository.findById(job_id)
				.orElseThrow(() ->
			    new JobNotFoundException(
			        "Offre avec id "
			        + job_id +
			        " introuvable"
			    )
			);
		
		return mapperInterface.jobToJobResponse(job);
	}
    
    
}