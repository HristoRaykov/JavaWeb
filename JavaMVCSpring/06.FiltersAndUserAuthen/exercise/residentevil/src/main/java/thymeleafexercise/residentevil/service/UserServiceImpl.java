package thymeleafexercise.residentevil.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import thymeleafexercise.residentevil.domain.entities.Role;
import thymeleafexercise.residentevil.domain.entities.User;
import thymeleafexercise.residentevil.domain.models.services.UserServiceModel;
import thymeleafexercise.residentevil.repository.RoleRepository;
import thymeleafexercise.residentevil.repository.UserRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserDetails user = userRepository.findByUsername(s).orElseThrow(() -> new UsernameNotFoundException("No such user."));
        return user;
    }

    @Override
    public boolean registerUser(UserServiceModel userServiceModel) {
        User user = modelMapper.map(userServiceModel, User.class);
        user.setPassword(bCryptPasswordEncoder.encode(userServiceModel.getPassword()));
        user.setAuthorities(getUserRoles());

        try {
            user = userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public List<UserServiceModel> finnAllUsers() {
        return Arrays.asList(modelMapper.map(userRepository.findAll(), UserServiceModel[].class));
    }

    @Override
    public UserServiceModel findUserById(String id) {
        return modelMapper.map(userRepository.findById(id).orElseThrow(()->new UsernameNotFoundException("No such user.")),
                UserServiceModel.class);
    }

    @Override
    public void save(UserServiceModel userServiceModel) {
        User user = modelMapper.map(userServiceModel,User.class);
        userRepository.save(user);
    }

    @Override
    public void editUserRoles(String id, Set<Role> roles) {
        User user = userRepository.findById(id).orElseThrow();
        user.setAuthorities(roles);
        userRepository.save(user);
    }

    private Set<Role> getUserRoles() {
        Set<Role> roles = new HashSet<>();
        if (roleRepository.count() == 0) {
            seedUserRoles();
        }
        if (userRepository.count() == 0) {
            roles.add(roleRepository.findByAuthority("ROLE_ADMIN").orElseThrow(() -> new AuthorizationServiceException("No role 'ADMIN'.")));
            roles.add(roleRepository.findByAuthority("ROLE_USER").orElseThrow(() -> new AuthorizationServiceException("No role 'USER'.")));
            roles.add(roleRepository.findByAuthority("ROLE_MODERATOR").orElseThrow(() -> new AuthorizationServiceException("No role 'MODERATOR'.")));
        } else {
            roles.add(roleRepository.findByAuthority("ROLE_USER").orElseThrow(() -> new AuthorizationServiceException("No role 'USER'.")));
        }
        return roles;
    }

    private void seedUserRoles() {
        Set<Role> roles = new HashSet<>();

        Role roleAdmin = new Role();
        Role roleModerator = new Role();
        Role roleUser = new Role();

        roleAdmin.setAuthority("ROLE_ADMIN");
        roleModerator.setAuthority("ROLE_MODERATOR");
        roleUser.setAuthority("ROLE_USER");

        roles.add(roleAdmin);
        roles.add(roleModerator);
        roles.add(roleUser);

        try {
            roleRepository.saveAll(roles);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
