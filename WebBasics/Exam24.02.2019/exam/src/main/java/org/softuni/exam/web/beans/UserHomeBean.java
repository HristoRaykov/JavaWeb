package org.softuni.exam.web.beans;

import org.modelmapper.ModelMapper;
import org.softuni.exam.domain.models.services.DocumentServiceModel;
import org.softuni.exam.domain.models.views.HomePageDocumentViewModel;
import org.softuni.exam.service.DocumentService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.stream.Collectors;

@Named
@RequestScoped
public class UserHomeBean {
	
	private List<HomePageDocumentViewModel> documentViewModels;
	
	private DocumentService documentService;
	private ModelMapper modelMapper;
	
	public UserHomeBean() {
	}
	
	@Inject
	public UserHomeBean(DocumentService documentService, ModelMapper modelMapper) {
		this.documentService = documentService;
		this.modelMapper = modelMapper;
	}
	
	@PostConstruct
	private void init() {
		List<DocumentServiceModel> documentServiceModels = this.documentService.getAllDocuments();
		documentViewModels = documentServiceModels.stream()
				.map(dsm -> {
					HomePageDocumentViewModel homePageDocumentViewModel = this.modelMapper.map(dsm, HomePageDocumentViewModel.class);
					if (dsm.getTitle().length()>12) {
						String partialTitle = dsm.getTitle().substring(0, 12) + "...";
						homePageDocumentViewModel.setPartialTitle(partialTitle);
					}else {
						homePageDocumentViewModel.setPartialTitle(dsm.getTitle());
					}
					return homePageDocumentViewModel;
				})
				.collect(Collectors.toList());
	}
	
	public List<HomePageDocumentViewModel> getDocumentViewModels() {
		return documentViewModels;
	}
	
	public void setDocumentViewModels(List<HomePageDocumentViewModel> documentViewModels) {
		this.documentViewModels = documentViewModels;
	}
	
}
