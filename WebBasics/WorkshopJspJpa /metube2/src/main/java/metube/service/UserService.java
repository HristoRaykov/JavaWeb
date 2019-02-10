package metube.service;

import metube.domain.models.service.UserServiceModel;

public interface UserService {

    boolean registerUser(UserServiceModel userServiceModel);

    UserServiceModel loginUser(UserServiceModel userServiceModel);

    UserServiceModel findUserByUsername(String username);
	
	void updateUser(UserServiceModel userServiceModel);
}
