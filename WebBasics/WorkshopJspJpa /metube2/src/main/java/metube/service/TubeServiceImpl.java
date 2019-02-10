package metube.service;

import metube.domain.entities.Tube;
import metube.domain.models.service.TubeServiceModel;
import metube.domain.models.service.UserServiceModel;
import metube.repository.TubeRepository;
import org.modelmapper.ModelMapper;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;


public class TubeServiceImpl implements TubeService {
	
	private final TubeRepository tubeRepository;
	private final UserService userService;
	private final ModelMapper modelMapper;
	
	@Inject
	public TubeServiceImpl(TubeRepository tubeRepository, UserService userService, ModelMapper modelMapper) {
		this.tubeRepository = tubeRepository;
		this.userService = userService;
		this.modelMapper = modelMapper;
	}
	
	@Override
	public boolean uploadTube(TubeServiceModel tubeServiceModel) {
		UserServiceModel userServiceModel = this.userService.findUserByUsername(tubeServiceModel.getUploader().getUsername());
		tubeServiceModel.setUploader(userServiceModel);
		userServiceModel.addTube(tubeServiceModel);
		
		Tube tube = this.modelMapper.map(tubeServiceModel, Tube.class);
		try {
			this.tubeRepository.save(tube);
		} catch (Exception e) {
			return false;
		}
//		userServiceModel.addTube(tubeServiceModel);
//		try {
//			this.userService.updateUser(userServiceModel);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return true;
	}
	
	@Override
	public TubeServiceModel findTubeById(String id) {
		Tube tube = this.tubeRepository.findById(id);
		
		if (tube == null) {
			throw new IllegalArgumentException();
		}
		
		return this.modelMapper.map(tube, TubeServiceModel.class);
	}
	
	@Override
	public List<TubeServiceModel> findAll() {
		List<Tube> tubes = this.tubeRepository.findAll();
		return Arrays.asList(this.modelMapper.map(tubes, TubeServiceModel[].class));
	}
	
	@Override
	public boolean updateTubeViews(TubeServiceModel tubeServiceModel) {
		UserServiceModel userServiceModel = this.userService.findUserByUsername(tubeServiceModel.getUploader().getUsername());
		tubeServiceModel.setUploader(userServiceModel);
		Tube tube = this.modelMapper.map(tubeServiceModel, Tube.class);
		
		try {
			this.tubeRepository.update(tube);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	@Override
	public List<TubeServiceModel> findAllPendingTubes() {
		List<Tube> tubes = this.tubeRepository.findAllPendingTubes();
		return Arrays.asList(this.modelMapper.map(tubes, TubeServiceModel[].class));
	}
	
	@Override
	public void updateTubeStatus(TubeServiceModel tubeServiceModel) {
		Tube tube = this.modelMapper.map(tubeServiceModel,Tube.class);
		
		this.tubeRepository.update(tube);
	}
	
	
}
