package org.softuni.exam.domain.models.views;

public class UserProfileViewModel {
	
	private String username;
	private String gender;
	private String genderImgName;
	
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getGenderImgName() {
		return genderImgName;
	}
	
	public void setGenderImgName(String genderImgName) {
		this.genderImgName = genderImgName;
	}
}
