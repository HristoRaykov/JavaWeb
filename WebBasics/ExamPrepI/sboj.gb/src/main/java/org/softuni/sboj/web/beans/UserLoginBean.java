package org.softuni.sboj.web.beans;

import org.apache.commons.codec.digest.DigestUtils;
import org.softuni.sboj.domain.models.binding.UserLoginBindingModel;
import org.softuni.sboj.domain.models.services.UserServiceModel;
import org.softuni.sboj.service.UserService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Named()
@RequestScoped
public class UserLoginBean extends BaseBean {
	
	private UserLoginBindingModel userLoginBindingModel;
	
	private UserService userService;
	
	public UserLoginBean() {
	}
	
	@Inject
	public UserLoginBean(UserService userService) {
		this.userService = userService;
	}
	
	@PostConstruct
	public void init() {
		this.userLoginBindingModel = new UserLoginBindingModel();
	}
	
	public UserLoginBindingModel getUserLoginBindingModel() {
		return this.userLoginBindingModel;
	}
	
	public void setUserLoginBindingModel(UserLoginBindingModel userLoginBindingModel) {
		this.userLoginBindingModel = userLoginBindingModel;
	}
	
	public void login() {
		Optional<UserServiceModel> userOptional = this.userService.getUserByUsername(this.userLoginBindingModel.getUsername());
		String hashedPassword =  DigestUtils.sha256Hex(this.userLoginBindingModel.getPassword());
		if (userOptional.isEmpty() || !userOptional.get().getPassword().equals(hashedPassword)) {
			this.redirect("/view/index.xhtml");
			return;
		}
		
		UserServiceModel user = userOptional.get();
		HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		httpSession.setAttribute("username", user.getUsername());
		httpSession.setAttribute("userId", user.getUsername());
		this.redirect("/view/loginuser/home.xhtml");
	}
}