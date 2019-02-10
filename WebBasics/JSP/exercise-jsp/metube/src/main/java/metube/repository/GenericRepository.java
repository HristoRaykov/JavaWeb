package metube.repository;

import metube.domain.entities.Tube;

import java.util.List;
import java.util.Optional;

public interface GenericRepository<E,K> {
	
	List<E> findAll();
	
	Optional<Tube> findByName(String name);
	
	void saveEntity(E entity);
	
	
	
}
