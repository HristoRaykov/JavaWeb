package springmvc.realestateagency.domain.models.service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class FindOfferServiceModel {

	private BigDecimal familyBudget;
	private String familyApartmentType;
	private String familyName;
	
	@NotNull
	@Positive
	public BigDecimal getFamilyBudget() {
		return familyBudget;
	}
	
	public void setFamilyBudget(BigDecimal familyBudget) {
		this.familyBudget = familyBudget;
	}
	
	
	@NotBlank
	public String getFamilyApartmentType() {
		return familyApartmentType;
	}
	
	public void setFamilyApartmentType(String familyApartmentType) {
		this.familyApartmentType = familyApartmentType;
	}
	
	@NotBlank
	public String getFamilyName() {
		return familyName;
	}
	
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
}
