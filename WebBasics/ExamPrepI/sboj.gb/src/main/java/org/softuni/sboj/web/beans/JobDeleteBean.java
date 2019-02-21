package org.softuni.sboj.web.beans;

import org.softuni.sboj.service.JobApplicationService;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named
public class JobDeleteBean extends BaseBean {
	
	private JobApplicationService jobApplicationService;
	
	public JobDeleteBean() {
	}
	
	@Inject
	public JobDeleteBean(JobApplicationService jobApplicationService) {
		this.jobApplicationService = jobApplicationService;
	}
	
	
	public void deleteJob(){
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String id = req.getParameter("id");
		this.jobApplicationService.deleteJobApplicationById(id);
		super.redirect("/view/loginuser/home.xhtml");
	}
}
