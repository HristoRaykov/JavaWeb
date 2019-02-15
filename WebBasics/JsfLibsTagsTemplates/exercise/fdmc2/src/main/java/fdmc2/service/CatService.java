package fdmc2.service;

import fdmc2.domain.models.services.CatServiceModel;

import java.util.List;

public interface CatService {
	
	
	boolean save(CatServiceModel catServiceModel);
	
	List<CatServiceModel> getAllCats();
	
	List<CatServiceModel> getAllCatsOrderedBy(String orderBy);
}
