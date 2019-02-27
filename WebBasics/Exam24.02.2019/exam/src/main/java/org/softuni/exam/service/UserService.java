package org.softuni.exam.service;

import org.softuni.exam.domain.models.services.UserServiceModel;

import java.util.List;
import java.util.Optional;

public interface UserService {
	
	boolean register(UserServiceModel userServiceModel);
	
	
	Optional<UserServiceModel> loginUser(UserServiceModel userServiceModel);
	
	List<UserServiceModel> getAllUsers();
	
	Optional<UserServiceModel> getUserByUsername(String username);
	
	@SuppressWarnings("Duplicates")
	Optional<UserServiceModel> getUserById(String id);
	
	Optional<UserServiceModel> createUser(UserServiceModel userServiceModel);
	
}
