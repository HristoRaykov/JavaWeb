package org.softuni.exam.web.beans;

import org.modelmapper.ModelMapper;
import org.softuni.exam.domain.models.binding.UserRegisterBindingModel;

import org.softuni.exam.domain.models.services.UserServiceModel;
import org.softuni.exam.service.UserService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Optional;

@Named
@RequestScoped
public class UserRegisterBean extends BaseBean {
    private UserRegisterBindingModel userRegisterBindingModel;

    private UserService userService;

    private ModelMapper modelMapper;

    public UserRegisterBean() {
    }

    @Inject
    public UserRegisterBean(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void init() {
        this.userRegisterBindingModel = new UserRegisterBindingModel();
    }

    public UserRegisterBindingModel getUserRegisterBindingModel() {
        return this.userRegisterBindingModel;
    }

    public void setUserRegisterBindingModel(UserRegisterBindingModel userRegisterBindingModel) {
        this.userRegisterBindingModel = userRegisterBindingModel;
    }

    public void register() {
        if(!this.userRegisterBindingModel.getPassword()
                .equals(this.userRegisterBindingModel.getConfirmPassword())) {
            this.redirect("/view/register.xhtml");
            return;
        }

        Optional<UserServiceModel> userServiceModel = this.userService.createUser(
                this.modelMapper.map(this.userRegisterBindingModel, UserServiceModel.class));
        if (userServiceModel.isPresent()) {
	        this.redirect("/view/login.xhtml");
        }else {
	        this.redirect("/view/register.xhtml");
        }
    }
}
