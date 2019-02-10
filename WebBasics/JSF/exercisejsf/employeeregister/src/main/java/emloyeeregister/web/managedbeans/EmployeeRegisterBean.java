package emloyeeregister.web.managedbeans;

import emloyeeregister.doman.models.binding.EmployeeRegisterBindingModel;
import emloyeeregister.doman.models.service.EmployeeServiceModel;
import emloyeeregister.service.EmployeeService;
import org.modelmapper.ModelMapper;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
@RequestScoped
public class EmployeeRegisterBean {
	
	private EmployeeRegisterBindingModel employeeRegBindMod;
	
	private EmployeeService employeeService;
	private ModelMapper modelMapper;
	
	public EmployeeRegisterBean() {
		employeeRegBindMod = new EmployeeRegisterBindingModel();
	}
	
	@Inject
	public EmployeeRegisterBean(EmployeeService employeeService, ModelMapper modelMapper) {
		this();
		this.employeeService = employeeService;
		this.modelMapper = modelMapper;
	}
	
	public EmployeeRegisterBindingModel getEmployeeRegBindMod() {
		return employeeRegBindMod;
	}
	
	public void setEmployeeRegBindMod(EmployeeRegisterBindingModel employeeRegBindMod) {
		this.employeeRegBindMod = employeeRegBindMod;
	}
	
	public void register() throws IOException {
		if (!this.validateInput()) {
			return;
		}
		EmployeeServiceModel employeeServiceModel = this.modelMapper.map(employeeRegBindMod, EmployeeServiceModel.class);
		this.employeeService.registerEmployee(employeeServiceModel);
		
		FacesContext.getCurrentInstance().getExternalContext().redirect("/");
	}
	
	private boolean validateInput() {
		return !this.employeeRegBindMod.getFirstName().isBlank() &&
				!this.employeeRegBindMod.getLastName().isBlank() &&
				!this.employeeRegBindMod.getPosition().isBlank() &&
				this.employeeRegBindMod.getSalary() != null &&
				this.employeeRegBindMod.getAge() != null;
	}
	
	
}
