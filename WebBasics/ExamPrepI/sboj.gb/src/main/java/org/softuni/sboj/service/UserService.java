package org.softuni.sboj.service;

import org.softuni.sboj.domain.models.services.UserServiceModel;

import java.util.List;
import java.util.Optional;

public interface UserService {
	
	boolean register(UserServiceModel userServiceModel);
	
	
	Optional<UserServiceModel> loginUser(UserServiceModel userServiceModel);
	
	List<UserServiceModel> getAllUsers();
	
	Optional<UserServiceModel> getUserByUsername(String username);
	
	Optional<UserServiceModel> createUser(UserServiceModel userServiceModel);
	
}
