package org.softuni.exam.web.beans;

import org.modelmapper.ModelMapper;
import org.softuni.exam.domain.models.services.UserServiceModel;
import org.softuni.exam.domain.models.views.UserFriendsViewModel;
import org.softuni.exam.service.UserService;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Named
public class UserFriendsBean {
	
	private List<UserFriendsViewModel> friendsViewModels;
	
	private UserService userService;
	private ModelMapper modelMapper;
	
	public UserFriendsBean() {
	}
	
	@Inject
	public UserFriendsBean(UserService userService, ModelMapper modelMapper) {
		this.userService = userService;
		this.modelMapper = modelMapper;
	}
	
	@PostConstruct
	private void initFriedsViewModel(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		String id = (String) session.getAttribute("userId");
		Optional<UserServiceModel> userOptional = this.userService.getUserById(id);
		List<UserServiceModel> friends = new ArrayList<>();
		if (userOptional.isPresent()){
			friends = userOptional.get().getFriends();
		}
		
		friendsViewModels = Arrays.asList(this.modelMapper.map(friends,UserFriendsViewModel[].class));
	}
	
	public List<UserFriendsViewModel> getFriendsViewModels() {
		return friendsViewModels;
	}
	
	public void setFriendsViewModels(List<UserFriendsViewModel> friendsViewModels) {
		this.friendsViewModels = friendsViewModels;
	}
}
