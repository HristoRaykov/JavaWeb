package org.softuni.sboj.web.beans;

import org.modelmapper.ModelMapper;
import org.softuni.sboj.domain.models.services.JobAppServiceModel;
import org.softuni.sboj.domain.models.views.JobDeleteViewModel;
import org.softuni.sboj.service.JobApplicationService;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Named
public class JobDeleteInfoBean extends BaseBean {
	
	private JobDeleteViewModel jobDeleteViewModel;
	
	private JobApplicationService jobApplicationService;
	private ModelMapper modelMapper;
	
	public JobDeleteInfoBean() {
	}
	
	@Inject
	public JobDeleteInfoBean(JobApplicationService jobApplicationService, ModelMapper modelMapper) {
		this.jobApplicationService = jobApplicationService;
		this.modelMapper = modelMapper;
	}
	
	@PostConstruct
	private void init(){
		String id = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("id");
		Optional<JobAppServiceModel> jobAppServiceModel = this.jobApplicationService.findById(id);
		jobAppServiceModel.ifPresent(jobAppServiceModel1 -> jobDeleteViewModel = this.modelMapper.map(jobAppServiceModel1, JobDeleteViewModel.class));
	}
	
	public JobDeleteViewModel getJobDeleteViewModel() {
		return jobDeleteViewModel;
	}
	
	public void setJobDeleteViewModel(JobDeleteViewModel jobDeleteViewModel) {
		this.jobDeleteViewModel = jobDeleteViewModel;
	}
	

}
