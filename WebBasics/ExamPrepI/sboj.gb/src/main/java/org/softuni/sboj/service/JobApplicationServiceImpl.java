package org.softuni.sboj.service;

import org.modelmapper.ModelMapper;
import org.softuni.sboj.domain.entities.JobApplication;
import org.softuni.sboj.domain.models.services.JobAppServiceModel;
import org.softuni.sboj.repository.JobApplicationRepository;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class JobApplicationServiceImpl implements JobApplicationService {
	
	private final JobApplicationRepository jobApplicationRepository;
	private final ModelMapper modelMapper;
	
	@Inject
	public JobApplicationServiceImpl(JobApplicationRepository jobApplicationRepository, ModelMapper modelMapper) {
		this.jobApplicationRepository = jobApplicationRepository;
		this.modelMapper = modelMapper;
	}
	
	
	@Override
	public List<JobAppServiceModel> getAllJobApplications() {
		List<JobApplication> jobApps = this.jobApplicationRepository.findAll();
		return Arrays.asList(this.modelMapper.map(jobApps, JobAppServiceModel[].class));
	}
	
	@Override
	public Optional<JobAppServiceModel> addJob(JobAppServiceModel jobAppServiceModel) {
		Optional<JobApplication> jobApplication = this.jobApplicationRepository
				.save(this.modelMapper.map(jobAppServiceModel, JobApplication.class));
		if (jobApplication.isEmpty()){
			return Optional.empty();
		}
		return Optional.of(this.modelMapper.map(jobApplication.get(),JobAppServiceModel.class));
	}
	
	@Override
	public void deleteJobApplicationById(String id) {
		JobApplication jobApplication = this.jobApplicationRepository.findById(id).get();
		this.jobApplicationRepository.delete(jobApplication);
	}
	
	@Override
	public Optional<JobAppServiceModel> findById(String id) {
		Optional<JobApplication> jobApplication = this.jobApplicationRepository.findById(id);
		if (jobApplication.isEmpty()) {
			return Optional.empty();
		}
		JobAppServiceModel jobAppServiceModel = this.modelMapper.map(jobApplication.get(), JobAppServiceModel.class);
		return Optional.of(jobAppServiceModel);
	}
}
