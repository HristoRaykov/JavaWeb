package springmvc.realestateagency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import springmvc.realestateagency.domain.entities.Offer;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, String> {
	
	@Query("select o from Offer o " +
			"where o.apartmentType=:apartmentType " +
			"and o.apartmentRent*(1+o.agencyCommission/100)<=:familyBudget")
	List<Offer> findAllByApartmentTypeAndFamilyBudget(@Param("apartmentType") String apartmentType,
	                                                  @Param("familyBudget") BigDecimal familyBudget);
	
}
