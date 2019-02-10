package emloyeeregister.service;

import emloyeeregister.doman.models.service.EmployeeServiceModel;

import java.io.IOException;
import java.util.List;

public interface EmployeeService {
	
	
	boolean registerEmployee(EmployeeServiceModel employeeServiceModel);
	
	List<EmployeeServiceModel> getAllEmployees();
	
	void remove(String employeeId) throws IOException;
}
