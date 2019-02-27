package org.softuni.exam.repository;

import org.softuni.exam.domain.entities.Document;

public interface DocumentRepository extends GenericRepository<Document,String> {
	
	
	boolean deleteDocumentById(String documentId);
}
