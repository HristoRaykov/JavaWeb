package org.softuni.sboj.service;

import org.softuni.sboj.domain.models.services.JobAppServiceModel;

import java.util.List;
import java.util.Optional;

public interface JobApplicationService {
	
	List<JobAppServiceModel> getAllJobApplications();
	
	Optional<JobAppServiceModel> addJob(JobAppServiceModel jobAppServiceModel);
	
	void deleteJobApplicationById(String id);
	
	Optional<JobAppServiceModel> findById(String id);
}
