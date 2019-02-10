package emloyeeregister.doman.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity(name = "employees")
public class Employee extends BaseEntity{
	
	private String firstName;
	private String lastName;
	private String position;
	private BigDecimal salary;
	private Integer age;
	
	public Employee() {
	}
	
	@Column(unique = true, nullable = false)
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Column
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Column
	public String getPosition() {
		return position;
	}
	
	public void setPosition(String position) {
		this.position = position;
	}
	
	@Column
	public BigDecimal getSalary() {
		return salary;
	}
	
	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}
	
	@Column
	public Integer getAge() {
		return age;
	}
	
	public void setAge(Integer age) {
		this.age = age;
	}
}
