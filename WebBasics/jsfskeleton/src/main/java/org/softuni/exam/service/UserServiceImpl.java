package org.softuni.exam.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import org.softuni.exam.domain.entities.User;
import org.softuni.exam.domain.models.services.UserServiceModel;
import org.softuni.exam.repository.UserRepository;
import org.softuni.exam.util.ValidatorUtil;


import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	private final ModelMapper modelMapper;
	private final ValidatorUtil validatorUtil;
	
	@Inject
	public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidatorUtil validatorUtil) {
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
		this.validatorUtil = validatorUtil;
	}
	
	
	@Override
	public boolean register(UserServiceModel userServiceModel) {
		User user = this.modelMapper.map(userServiceModel, User.class);
		user.setPassword(DigestUtils.sha256Hex(user.getPassword()));
		
		return this.userRepository.save(user).isPresent();
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
	
	@Override
	public Optional<UserServiceModel> createUser(UserServiceModel userServiceModel) {
		User user = this.modelMapper.map(userServiceModel,User.class);
		user.setPassword(DigestUtils.sha256Hex(userServiceModel.getPassword()));
		Optional<User> optionalUser =this.userRepository.save(user);
		if (optionalUser.isEmpty()){
			return Optional.empty();
		}
		
		return Optional.of(this.modelMapper.map(optionalUser.get(),UserServiceModel.class));
	}
}
