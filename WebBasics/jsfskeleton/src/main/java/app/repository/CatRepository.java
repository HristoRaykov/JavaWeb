package app.repository;

import app.domain.entities.Cat;

import java.util.List;

public interface CatRepository extends GenericRepository<Cat,String> {
	
	
	List<Cat> findAllOrderedBy(String columnName);
}
