package springmvc.springessentials.exodia.service;


import springmvc.springessentials.exodia.domain.models.services.UserServiceModel;

import java.util.List;
import java.util.Optional;

public interface UserService {
	
	Optional<UserServiceModel> register(UserServiceModel userServiceModel);
	
	
	Optional<UserServiceModel> loginUser(UserServiceModel userServiceModel);
	
	List<UserServiceModel> getAllUsers();
	
	Optional<UserServiceModel> getUserByUsername(String username);
	
	@SuppressWarnings("Duplicates")
	Optional<UserServiceModel> getUserById(String id);
	
	
	
}
