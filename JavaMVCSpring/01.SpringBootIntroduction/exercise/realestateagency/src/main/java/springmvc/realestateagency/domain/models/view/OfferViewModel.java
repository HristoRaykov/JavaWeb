package springmvc.realestateagency.domain.models.view;

import java.math.BigDecimal;

public class OfferViewModel {
	
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
	
	public BigDecimal getApartmentRent() {
		return apartmentRent;
	}
	
	public void setApartmentRent(BigDecimal apartmentRent) {
		this.apartmentRent = apartmentRent;
	}
	
	public String getApartmentType() {
		return apartmentType;
	}
	
	public void setApartmentType(String apartmentType) {
		this.apartmentType = apartmentType;
	}
	
	public BigDecimal getAgencyCommission() {
		return agencyCommission;
	}
	
	public void setAgencyCommission(BigDecimal agencyCommission) {
		this.agencyCommission = agencyCommission;
	}
}
