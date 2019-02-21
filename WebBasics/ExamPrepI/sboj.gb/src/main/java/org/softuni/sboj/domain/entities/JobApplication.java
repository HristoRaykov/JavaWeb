package org.softuni.sboj.domain.entities;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
@Table(name = "job_applications")
public class JobApplication extends BaseEntity {
	
	
	private Sector sector;
	private String profession;
	private BigDecimal salary;
	private String description;
	
	public JobApplication() {
	}
	
	@Column
	@Enumerated(value = EnumType.STRING)
	public Sector getSector() {
		return sector;
	}
	
	public void setSector(Sector sector) {
		this.sector = sector;
	}
	
	@Column
	public String getProfession() {
		return profession;
	}
	
	public void setProfession(String profession) {
		this.profession = profession;
	}
	
	@Column
	public BigDecimal getSalary() {
		return salary;
	}
	
	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}
	
	@Column
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}
