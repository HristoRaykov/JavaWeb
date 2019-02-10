package metube.repository;

import metube.domain.entities.Tube;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;

public class TubeRepositoryImpl implements TubeRepository {
	
	private static EntityManager entityManager = Persistence
			.createEntityManagerFactory("metube")
			.createEntityManager();
	
	private static final String FIND_ALL_QUERY = "select t from tubes t";
	private static final String FIND_BY_NAME_QUERY =
			"select t from tubes t where t.name=:name";
	
	public TubeRepositoryImpl() {
	}
	
	@Override
	public List<Tube> findAll() {
		return entityManager
				.createQuery(FIND_ALL_QUERY,Tube.class)
				.getResultList();
	}
	
	@Override
	public Optional<Tube> findByName(String name) {
		Optional<Tube> tube = Optional.empty();
		try {
			tube = Optional.ofNullable(entityManager
					.createQuery(FIND_BY_NAME_QUERY,Tube.class)
					.setParameter("name",name)
					.getSingleResult());
		} catch (NoResultException e) {
		
		}
		
		System.out.println();
		return tube;
	}
	
	@Override
	public void saveEntity(Tube entity) {
		entityManager.getTransaction().begin();
		entityManager.persist(entity);
		entityManager.getTransaction().commit();
	}
 
}
