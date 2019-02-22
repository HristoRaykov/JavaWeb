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
public class AddFriendBean extends BaseBean {
	
	private UserService userService;
	private ModelMapper modelMapper;
	
	public AddFriendBean() {
	}
	
	@Inject
	public AddFriendBean(UserService userService, ModelMapper modelMapper) {
		this.userService = userService;
		this.modelMapper = modelMapper;
	}
	
	@SuppressWarnings("Duplicates")
	public void addFriend(String friendId) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		String loginUserId = (String) session.getAttribute("userId");
		Optional<UserServiceModel> loginUserOptional = this.userService.getUserById(loginUserId);
		UserServiceModel loginUser = new UserServiceModel();
		if (loginUserOptional.isPresent()) {
			loginUser = loginUserOptional.get();
		}
		Optional<UserServiceModel> friendOptional = this.userService.getUserById(friendId);
		UserServiceModel friend = new UserServiceModel();
		if (friendOptional.isPresent()) {
			friend = friendOptional.get();
		}
		loginUser.addFriend(friend);
		friend.addFriend(loginUser);
		
		this.userService.updateUser(loginUser);
		this.userService.updateUser(friend);
		
		super.redirect("/view/loginuser/friends.xhtml");
	}
	
}
