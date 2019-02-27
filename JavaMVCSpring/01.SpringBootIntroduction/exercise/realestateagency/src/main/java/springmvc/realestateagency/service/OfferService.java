package springmvc.realestateagency.service;

import springmvc.realestateagency.domain.models.service.FindOfferServiceModel;
import springmvc.realestateagency.domain.models.service.OfferServiceModel;

import java.util.List;
import java.util.Optional;

public interface OfferService {
	
	Optional<OfferServiceModel> save(OfferServiceModel offerServiceModel);
	
	List<OfferServiceModel> getAllOffers();
	
	boolean findApartment(FindOfferServiceModel findOfferServiceModel);
}
