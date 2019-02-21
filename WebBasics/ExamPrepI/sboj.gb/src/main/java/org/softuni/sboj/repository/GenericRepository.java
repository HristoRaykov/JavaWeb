package org.softuni.sboj.repository;

import java.util.List;
import java.util.Optional;

public interface GenericRepository<E,K> {
	
	List<E> findAll();
	
	Optional<E> findById(K id);
	
	Optional<E> save(E entity);
	
	Long size();
	
}
