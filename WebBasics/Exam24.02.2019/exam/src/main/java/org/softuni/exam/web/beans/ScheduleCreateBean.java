package org.softuni.exam.web.beans;

import org.modelmapper.ModelMapper;
import org.softuni.exam.domain.models.binding.ScheduleBindingModel;
import org.softuni.exam.domain.models.services.DocumentServiceModel;
import org.softuni.exam.service.DocumentService;
import org.softuni.exam.service.UserService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Optional;

@Named
@RequestScoped
public class ScheduleCreateBean extends BaseBean {
	
	private ScheduleBindingModel scheduleBindingModel;
	
	private DocumentService documentService;
	private ModelMapper modelMapper;
	
	public ScheduleCreateBean() {
	}
	
	@Inject
	public ScheduleCreateBean(DocumentService documentService, ModelMapper modelMapper) {
		this.documentService = documentService;
		this.modelMapper = modelMapper;
	}
	
	@PostConstruct
	private void init() {
		this.scheduleBindingModel = new ScheduleBindingModel();
	}
	
	public ScheduleBindingModel getScheduleBindingModel() {
		return scheduleBindingModel;
	}
	
	public void setScheduleBindingModel(ScheduleBindingModel scheduleBindingModel) {
		this.scheduleBindingModel = scheduleBindingModel;
	}
	
	public void createSchedule() {
		DocumentServiceModel documentServiceModel = this.modelMapper.map(scheduleBindingModel, DocumentServiceModel.class);
		Optional<DocumentServiceModel> savedDocumentOptional = this.documentService.addDocumentToDb(documentServiceModel);
		if (savedDocumentOptional.isEmpty()){
			throw new IllegalArgumentException("Could not save document.");
		}
		DocumentServiceModel savedDocument = savedDocumentOptional.get();
		
		super.redirect("/view/details.xhtml?documentId="+savedDocument.getId());
	}
}
