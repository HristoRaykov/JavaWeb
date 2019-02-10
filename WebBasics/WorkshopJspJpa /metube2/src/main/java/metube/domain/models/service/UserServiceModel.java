package metube.domain.models.service;

import metube.domain.entities.enums.UserRole;

import java.util.List;

public class UserServiceModel {

    private String id;
    private String username;
    private String email;
    private String password;
    private List<TubeServiceModel> tubes;
    private UserRole userRole;

    public UserServiceModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<TubeServiceModel> getTubes() {
        return tubes;
    }

    public void setTubes(List<TubeServiceModel> tubes) {
        this.tubes = tubes;
    }
	
	public UserRole getUserRole() {
		return userRole;
	}
	
	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}
	
	public void addTube(TubeServiceModel tubeServiceModel){
    	this.tubes.add(tubeServiceModel);
	}
}
