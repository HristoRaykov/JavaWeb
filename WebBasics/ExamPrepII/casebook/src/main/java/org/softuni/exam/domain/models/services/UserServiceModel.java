package org.softuni.exam.domain.models.services;

import org.softuni.exam.domain.entities.Gender;

import java.util.List;

public class UserServiceModel {

    private String id;
    private String username;
    private String password;
	private Gender gender;
	private List<UserServiceModel> friends;
    

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
	
	public Gender getGender() {
		return gender;
	}
	
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	public List<UserServiceModel> getFriends() {
		return friends;
	}
	
	public void setFriends(List<UserServiceModel> friends) {
		this.friends = friends;
	}
	
	public void addFriend(UserServiceModel user){
    	this.friends.add(user);
	}
	
	public void removeFriend(UserServiceModel friend){
    	this.friends.remove(friend);
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		
		UserServiceModel that = (UserServiceModel) o;
		
		return getId() != null ? getId().equals(that.getId()) : that.getId() == null;
	}
	
	@Override
	public int hashCode() {
		return getId() != null ? getId().hashCode() : 0;
	}
}
