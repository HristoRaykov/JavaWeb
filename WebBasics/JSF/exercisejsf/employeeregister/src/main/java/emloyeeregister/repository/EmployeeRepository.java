package emloyeeregister.repository;

import emloyeeregister.doman.entites.Employee;

public interface EmployeeRepository extends GenericRepository<Employee,String> {
	
	
	boolean remove(String employeeId);
}
