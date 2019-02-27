package org.softuni.exam.service;

import org.modelmapper.ModelMapper;
import org.softuni.exam.domain.entities.Document;
import org.softuni.exam.domain.models.services.DocumentServiceModel;
import org.softuni.exam.repository.DocumentRepository;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DocumentServiceImpl implements DocumentService {
	
	private final DocumentRepository documentRepository;
	private final ModelMapper modelMapper;
	
	@Inject
	public DocumentServiceImpl(DocumentRepository documentRepository, ModelMapper modelMapper) {
		this.documentRepository = documentRepository;
		this.modelMapper = modelMapper;
	}
	
	
	@Override
	@SuppressWarnings("Duplicates")
	public Optional<DocumentServiceModel> addDocumentToDb(DocumentServiceModel documentServiceModel) {
		Document document = this.modelMapper.map(documentServiceModel,Document.class);
		Optional<Document> documentOptional = this.documentRepository.save(document);
		if (documentOptional.isEmpty()){
			return Optional.empty();
		}
		DocumentServiceModel savedDocument = this.modelMapper.map(documentOptional.get(),DocumentServiceModel.class);
		
		return Optional.of(savedDocument);
	}
	
	@Override
	@SuppressWarnings("Duplicates")
	public Optional<DocumentServiceModel> getDocumentById(String documentId) {
		Optional<Document> documentOptional = this.documentRepository.findById(documentId);
		if (documentOptional.isEmpty()){
			return Optional.empty();
		}
		DocumentServiceModel documentServiceModel = this.modelMapper.map(documentOptional.get(), DocumentServiceModel.class);
		return Optional.of(documentServiceModel);
		
	}
	
	@Override
	public List<DocumentServiceModel> getAllDocuments() {
		return Arrays.asList(this.modelMapper.map(this.documentRepository.findAll(), DocumentServiceModel[].class));
		
	}
	
	@Override
	public boolean delete(String documentId) {
		return this.documentRepository.deleteDocumentById(documentId);
	}
}
