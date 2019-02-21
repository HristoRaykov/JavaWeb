package org.softuni.sboj.repository;

import org.softuni.sboj.domain.entities.JobApplication;
import org.softuni.sboj.domain.entities.User;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;


public class JobApplicationRepositoryImpl implements JobApplicationRepository {
	
	private final EntityManager entityManager;
	
	@Inject
	public JobApplicationRepositoryImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	
	@Override
	public List<JobApplication> findAll() {
		this.entityManager.getTransaction().begin();
		List<JobApplication> jobApplications = this.entityManager
				.createQuery("SELECT ja FROM JobApplication ja ", JobApplication.class)
				.getResultList();
		this.entityManager.getTransaction().commit();
		return jobApplications;
	}
	
	@Override
	public Optional<JobApplication> findById(String id) {
		this.entityManager.getTransaction().begin();
		JobApplication jobApplication;
		try {
			jobApplication = this.entityManager.find(JobApplication.class, id);
		} catch (Exception e) {
			return Optional.empty();
		} finally {
			this.entityManager.getTransaction().commit();
		}
		return Optional.of(jobApplication);
	}
	
	@SuppressWarnings("Duplicates")
	@Override
	public Optional<JobApplication> save(JobApplication entity) {
		this.entityManager.getTransaction().begin();
		try {
			this.entityManager.persist(entity);
		} catch (Exception e) {
			return Optional.empty();
		} finally {
			this.entityManager.getTransaction().commit();
		}
		
		return Optional.of(entity);
	}
	
	@Override
	public Long size() {
		return null;
	}
	
	@Override
	public void delete(JobApplication jobApplication){
		this.entityManager.getTransaction().begin();
		try {
			this.entityManager.remove(jobApplication);
		} catch (Exception ignored) {
		
		} finally {
			this.entityManager.getTransaction().commit();
		}
	}
	

}
