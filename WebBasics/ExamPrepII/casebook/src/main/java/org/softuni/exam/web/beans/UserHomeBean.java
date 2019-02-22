package org.softuni.exam.web.beans;

import org.modelmapper.ModelMapper;
import org.softuni.exam.domain.models.services.UserServiceModel;
import org.softuni.exam.domain.models.views.UserHomeViewModel;
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
import java.util.stream.Collectors;

@Named
public class UserHomeBean {
	
	private List<UserHomeViewModel> users;
	
	private UserService userService;
	private ModelMapper modelMapper;
	
	public UserHomeBean() {
	}
	
	@Inject
	public UserHomeBean(UserService userService, ModelMapper modelMapper) {
		this.userService = userService;
		this.modelMapper = modelMapper;
	}
	
	@PostConstruct
	@SuppressWarnings("Duplicates")
	private void initUsers() {
		List<UserServiceModel> allUsers = this.userService.getAllUsers();
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		String loginUserId = (String) session.getAttribute("userId");
		Optional<UserServiceModel> loginUserOptional = this.userService.getUserById(loginUserId);
		UserServiceModel loginUser = new UserServiceModel();
		List<UserServiceModel> friends;
		if (loginUserOptional.isPresent()) {
			loginUser = loginUserOptional.get();
		}
		friends = loginUser.getFriends();
		List<UserServiceModel> homePageUsers = new ArrayList<>(allUsers);
		homePageUsers.removeAll(friends);
		homePageUsers.remove(loginUser);
		users = homePageUsers.stream()
				.map(userServiceModel -> {
					UserHomeViewModel userHomeViewModel = this.modelMapper.map(userServiceModel, UserHomeViewModel.class);
					userHomeViewModel.setGender(userServiceModel.getGender().name().toLowerCase());
					return userHomeViewModel;
				})
				.collect(Collectors.toList());
	}
	
	public List<UserHomeViewModel> getUsers() {
		return users;
	}
	
	public void setUsers(List<UserHomeViewModel> users) {
		this.users = users;
	}
}
