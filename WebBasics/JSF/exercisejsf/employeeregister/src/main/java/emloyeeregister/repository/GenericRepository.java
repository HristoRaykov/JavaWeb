package emloyeeregister.repository;

import emloyeeregister.doman.entites.Employee;

import java.util.List;
import java.util.Optional;

public interface GenericRepository<E,K> {
	
	List<E> findAll();
	
	Optional<Employee> findById(K id);
	
	Optional<Employee> save(E entity);
	
	Long size();
	
}
