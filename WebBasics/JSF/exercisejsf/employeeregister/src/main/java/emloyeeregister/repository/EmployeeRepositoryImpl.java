package emloyeeregister.repository;

import emloyeeregister.doman.entites.Employee;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

public class EmployeeRepositoryImpl implements EmployeeRepository {
	
	@PersistenceContext
	private final EntityManager entityManager;
	
	@Inject
	public EmployeeRepositoryImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	
	@Override
	public List<Employee> findAll() {
		this.entityManager.getTransaction().begin();
		List<Employee> allEmployees = this.entityManager
				.createQuery("SELECT e FROM employees e", Employee.class)
				.getResultList();
		this.entityManager.getTransaction().commit();
		
		return allEmployees;
	
	}
	
	@Override
	public Optional<Employee> findById(String id) {
		return Optional.ofNullable(this.entityManager.find(Employee.class,id));
	}
	
	@Override
	public Optional<Employee> save(Employee entity) {
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
		this.entityManager.getTransaction().begin();
		Long size = this.entityManager
				.createQuery("SELECT count(e) FROM employees e", Long.class)
				.getSingleResult();
		this.entityManager.getTransaction().commit();
		
		return size;
	}
	
	@Override
	public boolean remove(String employeeId) {
		this.entityManager.getTransaction().begin();
		try {
			Employee employee = this.entityManager.find(Employee.class,employeeId);
			this.entityManager.remove(employee);
		} catch (Exception e) {
			return false;
		}
		this.entityManager.getTransaction().commit();
		
		return true;
	}
}
