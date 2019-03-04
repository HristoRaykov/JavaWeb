package springmvc.springessentials.exodia.service;


import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springmvc.springessentials.exodia.domain.entities.User;
import springmvc.springessentials.exodia.domain.models.services.UserServiceModel;
import springmvc.springessentials.exodia.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	private final ModelMapper modelMapper;
	
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
	}
	
	
	@Override
	public Optional<UserServiceModel> register(UserServiceModel userServiceModel) {
		User user = this.modelMapper.map(userServiceModel, User.class);
		user.setPassword(DigestUtils.sha256Hex(user.getPassword()));
		
		try {
			user = this.userRepository.save(user);
			return Optional.of(this.modelMapper.map(user, UserServiceModel.class));
		} catch (Exception e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}
	
	@Override
	public Optional<UserServiceModel> loginUser(UserServiceModel userServiceModel) {
		Optional<User> user = this.userRepository.findByUsername(userServiceModel.getUsername());
		
		if (user.isEmpty()) {
			return Optional.empty();
		}
		
		if (!user.get().getPassword().equals(DigestUtils.sha256Hex(userServiceModel.getPassword()))){
			return Optional.empty();
		}
		
		return Optional.of(this.modelMapper.map(user.get(),UserServiceModel.class));
	}
	
	@Override
	public List<UserServiceModel> getAllUsers(){
		return Arrays.asList(this.modelMapper.map(this.userRepository.findAll(),UserServiceModel[].class));
	}
	
	@Override
	@SuppressWarnings("Duplicates")
	public Optional<UserServiceModel> getUserByUsername(String username) {
		Optional<User> user = this.userRepository.findByUsername(username);
		if (user.isEmpty()){
			return Optional.empty();
		}
		UserServiceModel userServiceModel = this.modelMapper.map(user.get(),UserServiceModel.class);
		return Optional.of(userServiceModel);
	}
	
	@Override
	@SuppressWarnings("Duplicates")
	public Optional<UserServiceModel> getUserById(String id) {
		Optional<User> user = this.userRepository.findById(id);
		if (user.isEmpty()){
			return Optional.empty();
		}
		UserServiceModel userServiceModel = this.modelMapper.map(user.get(), UserServiceModel.class);
		return Optional.of(userServiceModel);
	}
	
	
}
