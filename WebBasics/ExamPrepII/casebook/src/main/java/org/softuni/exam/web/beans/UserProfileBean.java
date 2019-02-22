package org.softuni.exam.web.beans;

import org.modelmapper.ModelMapper;
import org.softuni.exam.domain.models.services.UserServiceModel;
import org.softuni.exam.domain.models.views.UserProfileViewModel;
import org.softuni.exam.service.UserService;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Optional;

@Named
public class UserProfileBean extends BaseBean{
	
	private UserProfileViewModel userProfileViewModel;
	
	private UserService userService;
	private ModelMapper modelMapper;
	
	public UserProfileBean() {
	}
	
	@Inject
	public UserProfileBean(UserService userService, ModelMapper modelMapper) {
		this.userService = userService;
		this.modelMapper = modelMapper;
	}
	
	@PostConstruct
	private void initUserProfileViewModel() {
		String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
		
		Optional<UserServiceModel> userServiceModelOptional = this.userService.getUserById(id);
		
		userServiceModelOptional.ifPresent(userServiceModel -> {
				userProfileViewModel = this.modelMapper.map(userServiceModel, UserProfileViewModel.class);
				userProfileViewModel.setGender(userServiceModel.getGender().toString());
				userProfileViewModel.setGenderImgName(userServiceModel.getGender().name().toLowerCase());
				
		});
	}
	
	public UserProfileViewModel getUserProfileViewModel() {
		return userProfileViewModel;
	}
	
	public void setUserProfileViewModel(UserProfileViewModel userProfileViewModel) {
		this.userProfileViewModel = userProfileViewModel;
	}
	
	
}
