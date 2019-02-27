package org.softuni.exam.web.beans;

import org.modelmapper.ModelMapper;
import org.softuni.exam.domain.models.binding.ScheduleBindingModel;
import org.softuni.exam.domain.models.services.DocumentServiceModel;
import org.softuni.exam.domain.models.views.DocumentDetailsViewModel;
import org.softuni.exam.service.DocumentService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Named
@RequestScoped
public class DocumentDetailsBean extends BaseBean {
	
	private DocumentDetailsViewModel documentDetailsViewModel;
	
	private DocumentService documentService;
	private ModelMapper modelMapper;
	
	public DocumentDetailsBean() {
	}
	
	@Inject
	public DocumentDetailsBean(DocumentService documentService, ModelMapper modelMapper) {
		this.documentService = documentService;
		this.modelMapper = modelMapper;
	}
	
	@PostConstruct
	private void init() {
		String documentId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("documentId");
		Optional<DocumentServiceModel> documentServiceModelOptional = this.documentService.getDocumentById(documentId);
		if (documentServiceModelOptional.isEmpty()){
			throw new IllegalArgumentException("Could not find document with such id");
		}
		documentDetailsViewModel = this.modelMapper.map(documentServiceModelOptional.get(),DocumentDetailsViewModel.class);
	}
	
	public DocumentDetailsViewModel getDocumentDetailsViewModel() {
		return documentDetailsViewModel;
	}
	
	public void setDocumentDetailsViewModel(DocumentDetailsViewModel documentDetailsViewModel) {
		this.documentDetailsViewModel = documentDetailsViewModel;
	}
}
