package metube.service;

import metube.domain.models.service.TubeServiceModel;

import java.util.List;

public interface TubeService {

    boolean uploadTube(TubeServiceModel tubeServiceModel);

    TubeServiceModel findTubeById(String id);
	
	List<TubeServiceModel> findAll();
	
	boolean updateTubeViews(TubeServiceModel tubeServiceModel);
	
	List<TubeServiceModel> findAllPendingTubes();
	
	void updateTubeStatus(TubeServiceModel tubeServiceModel);
}
