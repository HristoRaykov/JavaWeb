package springmvc.realestateagency.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springmvc.realestateagency.domain.entities.Offer;
import springmvc.realestateagency.domain.models.service.FindOfferServiceModel;
import springmvc.realestateagency.domain.models.service.OfferServiceModel;
import springmvc.realestateagency.repository.OfferRepository;

import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class OfferServiceImpl implements OfferService {
	
	private final OfferRepository offerRepository;
	private final Validator validator;
	private final ModelMapper modelMapper;
	
	@Autowired
	public OfferServiceImpl(OfferRepository offerRepository, Validator validator, ModelMapper modelMapper) {
		this.offerRepository = offerRepository;
		this.validator = validator;
		this.modelMapper = modelMapper;
	}
	
	
	@Override
	public Optional<OfferServiceModel> save(OfferServiceModel offerServiceModel) {
		if (this.validator.validate(offerServiceModel).size()>0){
			return Optional.empty();
		}
		Offer offer = this.modelMapper.map(offerServiceModel, Offer.class);
		try {
			offer = this.offerRepository.save(offer);
		} catch (Exception e) {
			return Optional.empty();
		}
		return Optional.of(this.modelMapper.map(offer,OfferServiceModel.class));
	}
	
	@Override
	public List<OfferServiceModel> getAllOffers() {
		return Arrays.asList(this.modelMapper.map(this.offerRepository.findAll(), OfferServiceModel[].class));
	}
	
	@Override
	public boolean findApartment(FindOfferServiceModel findOfferServiceModel) {
		if (this.validator.validate(findOfferServiceModel).size()>0){
			return false;
		}
		
		String apartmentType = findOfferServiceModel.getFamilyApartmentType();
		BigDecimal familyBudget = findOfferServiceModel.getFamilyBudget();
		
		List<Offer> offers = this.offerRepository.findAllByApartmentTypeAndFamilyBudget(apartmentType,familyBudget);
		if (offers.isEmpty()){
			return false;
		}
		Offer offer = offers.get(0);
		try {
			this.offerRepository.delete(offer);
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}
}
