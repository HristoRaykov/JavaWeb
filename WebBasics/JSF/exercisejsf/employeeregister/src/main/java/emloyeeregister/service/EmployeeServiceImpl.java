package emloyeeregister.service;

import emloyeeregister.doman.entites.Employee;
import emloyeeregister.doman.models.service.EmployeeServiceModel;
import emloyeeregister.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class EmployeeServiceImpl implements EmployeeService {
	
	private final EmployeeRepository employeeRepository;
	private final ModelMapper modelMapper;
	
	@Inject
	public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
		this.employeeRepository = employeeRepository;
		this.modelMapper = modelMapper;
	}
	
	@Override
	public boolean registerEmployee(EmployeeServiceModel employeeServiceModel) {
		Employee employee = this.modelMapper.map(employeeServiceModel, Employee.class);
		
		Optional<Employee> employeeOptional = this.employeeRepository.save(employee);
		
		return employeeOptional.isPresent();
	}
	
	@Override
	public List<EmployeeServiceModel> getAllEmployees(){
		return Arrays.asList(this.modelMapper
				.map(this.employeeRepository.findAll(),EmployeeServiceModel[].class));
	}
	
	@Override
	public void remove(String employeeId) throws IOException {
		this.employeeRepository.remove(employeeId);
	}
	
	
}
