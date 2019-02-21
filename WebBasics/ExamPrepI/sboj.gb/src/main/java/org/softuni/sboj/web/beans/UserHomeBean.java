package org.softuni.sboj.web.beans;

import org.modelmapper.ModelMapper;
import org.softuni.sboj.domain.models.services.JobAppServiceModel;
import org.softuni.sboj.domain.models.views.JobAppHomePageViewModel;
import org.softuni.sboj.service.JobApplicationService;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.List;

@Named
public class UserHomeBean {

	private List<JobAppHomePageViewModel> jobApps;
	
	private JobApplicationService jobApplicationService;
	private ModelMapper modelMapper;
	
	public UserHomeBean() {
	}
	
	@Inject
	public UserHomeBean(JobApplicationService jobApplicationService, ModelMapper modelMapper) {
		this.jobApplicationService = jobApplicationService;
		this.modelMapper = modelMapper;
	}
	
	@PostConstruct
	private void initJobApps(){
		List<JobAppServiceModel> jobAppServiceModels= this.jobApplicationService.getAllJobApplications();
		jobApps = Arrays.asList(this.modelMapper.map(jobAppServiceModels,JobAppHomePageViewModel[].class));
	}
	
	public List<JobAppHomePageViewModel> getJobApps() {
		return jobApps;
	}
	
	public void setJobApps(List<JobAppHomePageViewModel> jobApps) {
		this.jobApps = jobApps;
	}
	
	
	
}
