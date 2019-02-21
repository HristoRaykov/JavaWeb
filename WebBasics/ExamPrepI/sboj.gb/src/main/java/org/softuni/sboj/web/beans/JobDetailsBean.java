package org.softuni.sboj.web.beans;

import org.modelmapper.ModelMapper;
import org.softuni.sboj.domain.models.services.JobAppServiceModel;
import org.softuni.sboj.domain.models.views.JobDeleteViewModel;
import org.softuni.sboj.domain.models.views.JobDetailsViewModel;
import org.softuni.sboj.service.JobApplicationService;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Named
public class JobDetailsBean extends BaseBean {
	
	private JobDetailsViewModel jobDetailsViewModel;
	
	private JobApplicationService jobApplicationService;
	private ModelMapper modelMapper;
	
	public JobDetailsBean() {
	}
	
	@Inject
	public JobDetailsBean(JobApplicationService jobApplicationService, ModelMapper modelMapper) {
		this.jobApplicationService = jobApplicationService;
		this.modelMapper = modelMapper;
	}
	
	@PostConstruct
	private void init(){
		String id = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("id");
		Optional<JobAppServiceModel> jobAppServiceModel = this.jobApplicationService.findById(id);
		jobAppServiceModel.ifPresent(jobAppServiceModel1 -> jobDetailsViewModel = this.modelMapper.map(jobAppServiceModel1, JobDetailsViewModel.class));
	}
	
	public JobDetailsViewModel getJobDetailsViewModel() {
		return jobDetailsViewModel;
	}
	
	public void setJobDetailsViewModel(JobDetailsViewModel jobDetailsViewModel) {
		this.jobDetailsViewModel = jobDetailsViewModel;
	}
}
