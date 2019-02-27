package org.softuni.exam.repository;

import org.softuni.exam.domain.entities.Document;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class DocumentRepositoryImpl implements DocumentRepository {
	
	private final EntityManager entityManager;
	
	@Inject
	public DocumentRepositoryImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	
	@Override
	public List<Document> findAll() {
		this.entityManager.getTransaction().begin();
		List<Document> documents = this.entityManager
				.createQuery("SELECT d FROM Document d ", Document.class)
				.getResultList();
		this.entityManager.getTransaction().commit();
		return documents;
	}
	
	@Override
	public Optional<Document> findById(String id) {
		this.entityManager.getTransaction().begin();
		Document document;
		try {
			document = this.entityManager.find(Document.class, id);
			this.entityManager.getTransaction().commit();
		} catch (Exception e) {
			this.entityManager.getTransaction().rollback();
			return Optional.empty();
		}
		
		return Optional.of(document);
	}
	
	@Override
	@SuppressWarnings("Duplicates")
	public Optional<Document> save(Document entity) {
		this.entityManager.getTransaction().begin();
		try {
			this.entityManager.persist(entity);
			this.entityManager.getTransaction().commit();
		} catch (Exception e) {
			this.entityManager.getTransaction().rollback();
			return Optional.empty();
		}
		
		return Optional.of(entity);
	}
	
	@Override
	public Long size() {
		return (long) this.findAll().size();
	}
	
	@Override
	public boolean deleteDocumentById(String documentId) {
		this.entityManager.getTransaction().begin();
		try {
			this.entityManager
					.createQuery("delete from Document d where d.id = :documentId")
					.setParameter("documentId", documentId)
					.executeUpdate();
			this.entityManager.getTransaction().commit();
		} catch (Exception e) {
			this.entityManager.getTransaction().rollback();
			return false;
		}
		
		return true;
	}
}
