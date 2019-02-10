package metube.service;

import metube.domain.entities.Tube;
import metube.domain.models.services.TubeServiceModel;
import metube.repository.TubeRepository;
import metube.util.ModelMapper;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TubeServiceImpl implements TubeService {
	
	private final TubeRepository tubeRepository;
	private final ModelMapper modelMapper;
	
	@Inject
	public TubeServiceImpl(TubeRepository tubeRepository, ModelMapper modelMapper) {
		this.tubeRepository = tubeRepository;
		this.modelMapper = modelMapper;
	}
	
	
	@Override
	public TubeServiceModel getTubeByName(String tubeName) {
		Optional<Tube> tube = this.tubeRepository.findByName(tubeName);
		
		return this.modelMapper.map(tube,TubeServiceModel.class);
	}
	
	@Override
	public void saveTube(TubeServiceModel tubeServiceModel) {
		Tube tube = this.modelMapper.map(tubeServiceModel,Tube.class);
		this.tubeRepository.saveEntity(tube);
	}
	
	@Override
	public List<TubeServiceModel> findAll() {
		List<Tube> tubes = this.tubeRepository.findAll();
		return Arrays.asList(this.modelMapper.map(tubes, TubeServiceModel[].class));
	}
	
	
}
