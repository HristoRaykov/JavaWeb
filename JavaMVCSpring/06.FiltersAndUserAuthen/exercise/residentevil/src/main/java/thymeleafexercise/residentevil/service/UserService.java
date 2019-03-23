package thymeleafexercise.residentevil.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import thymeleafexercise.residentevil.domain.entities.Role;
import thymeleafexercise.residentevil.domain.models.services.UserServiceModel;

import java.util.List;
import java.util.Set;


public interface UserService extends UserDetailsService {

    boolean registerUser(UserServiceModel user);


    List<UserServiceModel> finnAllUsers();

    UserServiceModel findUserById(String id);

    void save(UserServiceModel userServiceModel);

    void editUserRoles(String id, Set<Role> roles);
}
