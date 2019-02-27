package springmvc.realestateagency.domain.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "offers")
public class Offer {
	
	private String id;
	private BigDecimal apartmentRent;
	private String apartmentType;
	private BigDecimal agencyCommission;
	
	@Id
	@GeneratedValue(generator = "uuid-string")
	@GenericGenerator(name = "uuid-string",strategy = "org.hibernate.id.UUIDGenerator")
	@Column(updatable = false,nullable = false)
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "apartment_rent",nullable = false)
	public BigDecimal getApartmentRent() {
		return apartmentRent;
	}
	
	public void setApartmentRent(BigDecimal apartmentRent) {
		this.apartmentRent = apartmentRent;
	}
	
	public String getApartmentType() {
		return apartmentType;
	}
	
	@Column(name = "apartment_type",nullable = false)
	public void setApartmentType(String apartmentType) {
		this.apartmentType = apartmentType;
	}
	
	public BigDecimal getAgencyCommission() {
		return agencyCommission;
	}
	
	@Column(name = "agency_commission",nullable = false)
	public void setAgencyCommission(BigDecimal agencyCommission) {
		this.agencyCommission = agencyCommission;
	}
}
