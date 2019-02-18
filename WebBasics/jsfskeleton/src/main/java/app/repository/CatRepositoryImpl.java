package app.repository;

import app.domain.entities.Cat;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class CatRepositoryImpl implements CatRepository {
	
	private static final String SEKECT_ALL_QUERY = "select c from Cat c";
	private static final String SEKECT_ALL_ORDERED_BY_QUERY = "select c from Cat c order by c.%s";
	
	private final EntityManager entityManager;
	
	@Inject
	public CatRepositoryImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	public List<Cat> findAll() {
		return this.entityManager
				.createQuery(SEKECT_ALL_QUERY, Cat.class)
				.getResultList();
	}
	
	@Override
	public List<Cat> findAllOrderedBy(String columnName) {
		return this.entityManager
				.createQuery(String.format(SEKECT_ALL_ORDERED_BY_QUERY,columnName), Cat.class)
				.getResultList();
	}
	
	
	@Override
	public Optional<Cat> findById(String id) {
		return Optional.ofNullable(this.entityManager.find(Cat.class, id));
	}
	
	@Override
	public Optional<Cat> save(Cat entity) {
		this.entityManager.getTransaction().begin();
		try {
			this.entityManager.persist(entity);
		} catch (Exception e) {
			return Optional.empty();
		}
		this.entityManager.getTransaction().commit();
		
		return Optional.of(entity);
	}
	
	@Override
	public Long size() {
		return (long) this.findAll().size();
	}
	
}
