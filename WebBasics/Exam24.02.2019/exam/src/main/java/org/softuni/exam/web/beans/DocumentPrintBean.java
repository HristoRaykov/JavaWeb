package org.softuni.exam.web.beans;

import org.modelmapper.ModelMapper;
import org.softuni.exam.domain.models.services.DocumentServiceModel;
import org.softuni.exam.domain.models.views.DocumentDetailsViewModel;
import org.softuni.exam.domain.models.views.DocumentPrintViewModel;
import org.softuni.exam.service.DocumentService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Optional;

@Named
@RequestScoped
public class DocumentPrintBean extends BaseBean {
	
	private DocumentPrintViewModel documentPrintViewModel;
	private String documentId;
	
	private DocumentService documentService;
	private ModelMapper modelMapper;
	
	public DocumentPrintBean() {
	}
	
	@Inject
	public DocumentPrintBean(DocumentService documentService, ModelMapper modelMapper) {
		this.documentService = documentService;
		this.modelMapper = modelMapper;
	}
	
	@PostConstruct
	@SuppressWarnings("Duplicates")
	private void init() {
		documentId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("documentId");
		Optional<DocumentServiceModel> documentServiceModelOptional = this.documentService.getDocumentById(documentId);
		if (documentServiceModelOptional.isEmpty()){
			throw new IllegalArgumentException("Could not find document with such id");
		}
		documentPrintViewModel = this.modelMapper.map(documentServiceModelOptional.get(),DocumentPrintViewModel.class);
	}
	
	public DocumentPrintViewModel getDocumentPrintViewModel() {
		return documentPrintViewModel;
	}
	
	public void setDocumentPrintViewModel(DocumentPrintViewModel documentPrintViewModel) {
		this.documentPrintViewModel = documentPrintViewModel;
	}
	
	public void print(){
		if (this.documentService.delete(documentId)){
			super.redirect("/view/home.xhtml");
		} else {
			throw new IllegalArgumentException("Unsuccessful deleteDocumentById");
		}
	}
}
