package metube.service;

import metube.domain.models.services.TubeServiceModel;

import java.util.List;

public interface TubeService {
	TubeServiceModel getTubeByName(String tubeName);
	
	void saveTube(TubeServiceModel tubeServiceModel);
	
	List<TubeServiceModel> findAll();
}
