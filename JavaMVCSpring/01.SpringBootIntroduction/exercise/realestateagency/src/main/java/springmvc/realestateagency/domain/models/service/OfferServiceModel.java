package springmvc.realestateagency.domain.models.service;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class OfferServiceModel {
	
	private String id;
	private BigDecimal apartmentRent;
	private String apartmentType;
	private BigDecimal agencyCommission;
	
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@NotNull
	@Positive
	public BigDecimal getApartmentRent() {
		return apartmentRent;
	}
	
	public void setApartmentRent(BigDecimal apartmentRent) {
		this.apartmentRent = apartmentRent;
	}
	
	@NotNull
	@NotBlank
	public String getApartmentType() {
		return apartmentType;
	}
	
	public void setApartmentType(String apartmentType) {
		this.apartmentType = apartmentType;
	}
	
	@NotNull
	@PositiveOrZero
	@DecimalMax("100")
	public BigDecimal getAgencyCommission() {
		return agencyCommission;
	}
	
	public void setAgencyCommission(BigDecimal agencyCommission) {
		this.agencyCommission = agencyCommission;
	}
}
