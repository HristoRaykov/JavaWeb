package org.softuni.sboj.repository;

import org.softuni.sboj.domain.entities.User;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {
	
	private final EntityManager entityManager;
	
	@Inject
	public UserRepositoryImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	@SuppressWarnings("Duplicates")
	public Optional<User> save(User user) {
		this.entityManager.getTransaction().begin();
		try {
			this.entityManager.persist(user);
		} catch (Exception e) {
			return Optional.empty();
		} finally {
			this.entityManager.getTransaction().commit();
		}
		
		return Optional.of(user);
	}
	
	@Override
	public List<User> findAll() {
		this.entityManager.getTransaction().begin();
		List<User> users = this.entityManager
				.createQuery("SELECT u FROM User u ", User.class)
				.getResultList();
		this.entityManager.getTransaction().commit();
		return users;
	}
	
	@Override
	public Optional<User> findById(String id) {
		this.entityManager.getTransaction().begin();
		User user;
		try {
			user = this.entityManager.find(User.class, id);
		} catch (Exception e) {
			return Optional.empty();
		} finally {
			this.entityManager.getTransaction().commit();
		}
		return Optional.of(user);
	}
	
	@Override
	public Long size() {
		return (long) this.findAll().size();
	}
	
	@Override
	public Optional<User> findByUsername(String username) {
		User user;
		this.entityManager.getTransaction().begin();
		try {
			user = this.entityManager
					.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
					.setParameter("username", username)
					.getSingleResult();
		} catch (Exception e) {
			return Optional.empty();
		} finally {
			this.entityManager.getTransaction().commit();
		}
		return Optional.of(user);
	}
}
