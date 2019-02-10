package emloyeeregister.web.managedbeans;

import emloyeeregister.doman.models.service.EmployeeServiceModel;
import emloyeeregister.doman.models.view.AllEmployeeViewModel;
import emloyeeregister.service.EmployeeService;
import org.modelmapper.ModelMapper;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

@Named
@RequestScoped
public class AllEmployeesBean {
	
	
	private EmployeeService employeeService;
	private ModelMapper modelMapper;
	private List<AllEmployeeViewModel> allEmployeeViewModels;
	
	public AllEmployeesBean() {
	}
	
	@Inject
	public AllEmployeesBean(EmployeeService employeeService, ModelMapper modelMapper) {
		this.employeeService = employeeService;
		this.modelMapper = modelMapper;
		this.getEmployeesList();
	}
	
	public List<AllEmployeeViewModel> getAllEmployeeViewModels() {
		return allEmployeeViewModels;
	}
	
	public void setAllEmployeeViewModels(List<AllEmployeeViewModel> allEmployeeViewModels) {
		this.allEmployeeViewModels = allEmployeeViewModels;
	}
	
	public void remove(String employeeId) throws IOException {
		this.employeeService.remove(employeeId);
		
		FacesContext.getCurrentInstance().getExternalContext().redirect("/");
	}
	
	public String getTotalMoneyNeeded() {
		BigDecimal totalMoney = getSumOfSalaries();
		return String.format("$%.2f",totalMoney);
	}
	

	
	public String getAverageSalary(){
		BigDecimal totalMoney = getSumOfSalaries();
		if (totalMoney.equals(BigDecimal.ZERO)){
			return "$0.00";
		}
		return String.format("$%.2f",totalMoney.divide(BigDecimal.valueOf(this.allEmployeeViewModels.size()),2, RoundingMode.HALF_UP));
	}
	
	private BigDecimal getSumOfSalaries() {
		return this.allEmployeeViewModels.stream()
				.map(AllEmployeeViewModel::getSalary)
				.reduce(BigDecimal::add)
				.orElse(BigDecimal.ZERO);
	}
	
	private void getEmployeesList() {
		List<EmployeeServiceModel> employeeServiceModels = this.employeeService.getAllEmployees();
		allEmployeeViewModels = Arrays.asList(this.modelMapper.map(employeeServiceModels, AllEmployeeViewModel[].class));
	}
}
