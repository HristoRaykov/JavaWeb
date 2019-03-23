package thymeleafexercise.residentevil.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import thymeleafexercise.residentevil.domain.models.bindings.RegisterBindinModel;
import thymeleafexercise.residentevil.domain.models.services.UserServiceModel;
import thymeleafexercise.residentevil.service.UserService;

import javax.validation.Valid;

@Controller
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/register")
    @PreAuthorize(value = "isAnonymous()")
    public String register(@ModelAttribute(name = "model") RegisterBindinModel model){

        return "register";
    }

    @PostMapping("/register")
    public String registerPost(@Valid @ModelAttribute(name = "model") RegisterBindinModel model, BindingResult bindingResult){
        UserServiceModel user = modelMapper.map(model, UserServiceModel.class);

        if (bindingResult.hasErrors()){
            return "register";
        }

        if (!model.getPassword().equals(model.getConfirmPassword())){
            return "register";
        }

        try {
            userService.registerUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return "register";
        }

        return "redirect:/login";
    }

    @GetMapping("/login")
    @PreAuthorize(value = "isAnonymous()")
    public String login(){

        return "login";
    }

}
