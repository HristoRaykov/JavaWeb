package org.softuni.exam.web.beans;

import org.modelmapper.ModelMapper;
import org.softuni.exam.domain.models.services.UserServiceModel;
import org.softuni.exam.service.UserService;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Named
public class UnfriendBean extends BaseBean {
	
	private UserService userService;
	private ModelMapper modelMapper;
	
	public UnfriendBean() {
	}
	
	@Inject
	public UnfriendBean(UserService userService, ModelMapper modelMapper) {
		this.userService = userService;
		this.modelMapper = modelMapper;
	}
	
	@SuppressWarnings("Duplicates")
	public void removeFriend(String friendId) {
		Optional<UserServiceModel> userServiceModelOptional = this.userService.getUserById(friendId);
		UserServiceModel friend = new UserServiceModel();
		if (userServiceModelOptional.isPresent()) {
			friend = userServiceModelOptional.get();
		}
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		String loginUserId = (String) session.getAttribute("userId");
		Optional<UserServiceModel> loginUserOptional = this.userService.getUserById(loginUserId);
		UserServiceModel loginUser = new UserServiceModel();
		if (loginUserOptional.isPresent()) {
			loginUser = loginUserOptional.get();
		}
		
		loginUser.removeFriend(friend);
		friend.removeFriend(loginUser);
		
		this.userService.updateUser(loginUser);
		this.userService.updateUser(friend);
		
		super.redirect("/view/loginuser/friends.xhtml");
	}
	
}
