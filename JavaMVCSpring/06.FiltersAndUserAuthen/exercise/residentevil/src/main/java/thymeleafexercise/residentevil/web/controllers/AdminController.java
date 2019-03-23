package thymeleafexercise.residentevil.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import thymeleafexercise.residentevil.domain.entities.Role;
import thymeleafexercise.residentevil.domain.models.services.UserServiceModel;
import thymeleafexercise.residentevil.domain.models.bindings.EditUserBindingModel;
import thymeleafexercise.residentevil.domain.models.views.UserViewModel;
import thymeleafexercise.residentevil.repository.RoleRepository;
import thymeleafexercise.residentevil.service.UserService;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class AdminController {

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AdminController(UserService userService, RoleRepository roleRepository, ModelMapper modelMapper) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/users")
    public String users(Model model, Principal principal) {
        List<UserServiceModel> userServiceModels = userService.finnAllUsers();
        userServiceModels = userServiceModels.stream()
                .filter(user -> !user.getUsername().equals(principal.getName()))
                .collect(Collectors.toList());
        List<UserViewModel> users = Arrays.asList(modelMapper.map(userServiceModels, UserViewModel[].class));
        model.addAttribute("users", users);

        return "users";
    }

    @GetMapping("/user/{id}/edit")
    public String editUserRoles(@PathVariable(name = "id") String id, @ModelAttribute EditUserBindingModel user, Model model) {
        List<Role> roles = roleRepository.findAll();
        try {
            UserServiceModel userServiceModel = userService.findUserById(id);
            user = modelMapper.map(userServiceModel, EditUserBindingModel.class);
        } catch (Exception e) {
            e.printStackTrace();
            return "users";
        }
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "edit-user";
    }

    @PostMapping("/user/{id}/edit")
    public String editUserRolesPost(@PathVariable(name = "id") String id, @ModelAttribute EditUserBindingModel user) {
//        UserServiceModel userServiceModel = userService.findUserById(id);
        Set<Role> roles = user.getAuthorities().stream().map(roleId -> roleRepository.findById(roleId).get()).collect(Collectors.toSet());
        try {
            userService.editUserRoles(id, roles);
        } catch (Exception e) {
            e.printStackTrace();
            return "edit-user";
        }


        return "redirect:/users";
    }

}
