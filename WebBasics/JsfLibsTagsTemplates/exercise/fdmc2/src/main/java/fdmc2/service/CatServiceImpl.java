package fdmc2.service;

import fdmc2.domain.entities.Cat;
import fdmc2.domain.models.services.CatServiceModel;
import fdmc2.repository.CatRepository;
import fdmc2.util.ValidatorUtil;
import org.modelmapper.ModelMapper;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CatServiceImpl implements CatService {
	
	private final CatRepository catRepository;
	private final ModelMapper modelMapper;
	private final ValidatorUtil validatorUtil;
	
	@Inject
	public CatServiceImpl(CatRepository catRepository, ModelMapper modelMapper, ValidatorUtil validatorUtil) {
		this.catRepository = catRepository;
		this.modelMapper = modelMapper;
		this.validatorUtil = validatorUtil;
	}
	
	@Override
	public boolean save(CatServiceModel catServiceModel){
		Cat cat = this.modelMapper.map(catServiceModel,Cat.class);
		
		Optional<Cat> savedCat = this.catRepository.save(cat);
		
		return savedCat.isPresent();
	}
	
	@Override
	public List<CatServiceModel> getAllCats() {
		List<Cat> cats = this.catRepository.findAll();
		return Arrays.asList(this.modelMapper.map(cats, CatServiceModel[].class));
	}
	
	@Override
	public List<CatServiceModel> getAllCatsOrderedBy(String orderBy) {
		List<Cat> cats = this.catRepository.findAllOrderedBy(orderBy);
		return Arrays.asList(this.modelMapper.map(cats,CatServiceModel[].class));
	}
	
	
}
