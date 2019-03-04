package springmvc.springessentials.exodia.service;

import springmvc.springessentials.exodia.domain.models.services.DocumentServiceModel;

import java.util.List;
import java.util.Optional;

public interface DocumentService {
	
	Optional<DocumentServiceModel> addDocumentToDb(DocumentServiceModel documentServiceModel);
	
	Optional<DocumentServiceModel> getDocumentById(String documentId);
	
	List<DocumentServiceModel> getAllDocuments();
	
	boolean delete(String documentId);
	
	String retrieveDocumentAsHtml(String id);
}
