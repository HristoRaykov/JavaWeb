package org.softuni.sboj.web.beans;

import org.modelmapper.ModelMapper;
import org.softuni.sboj.domain.entities.Sector;
import org.softuni.sboj.domain.models.binding.JobAddBindingModel;
import org.softuni.sboj.domain.models.services.JobAppServiceModel;
import org.softuni.sboj.service.JobApplicationService;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Optional;

@Named
public class JobAddBean extends BaseBean{
	
	private JobAddBindingModel jobAddBindingModel;
	
	private JobApplicationService jobApplicationService;
	private ModelMapper modelMapper;
	
	public JobAddBean() {
	}
	
	@Inject
	public JobAddBean(JobApplicationService jobApplicationService, ModelMapper modelMapper) {
		this.jobApplicationService = jobApplicationService;
		this.modelMapper = modelMapper;
	}
	
	
	@PostConstruct
	private void initJobAddBindingModel(){
		this.jobAddBindingModel = new JobAddBindingModel();
	}
	
	public JobAddBindingModel getJobAddBindingModel() {
		return jobAddBindingModel;
	}
	
	public void setJobAddBindingModel(JobAddBindingModel jobAddBindingModel) {
		this.jobAddBindingModel = jobAddBindingModel;
	}
	
	public void addJob(){
		JobAppServiceModel jobAppServiceModel = this.modelMapper.map(this.jobAddBindingModel,JobAppServiceModel.class);
		jobAppServiceModel.setSector(Sector.valueOf(this.jobAddBindingModel.getSector()));
		Optional<JobAppServiceModel> savedJobApplication = this.jobApplicationService.addJob(jobAppServiceModel);
		super.redirect("/view/loginuser/home.xhtml");
	}
	
	
}
