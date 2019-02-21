package org.softuni.sboj.domain.models.views;

import org.softuni.sboj.domain.entities.Sector;

public class JobAppHomePageViewModel {
	
	private String id;
	private String sector;
	private String profession;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getSector() {
		return sector;
	}
	
	public void setSector(String sector) {
		this.sector = sector;
	}
	
	public String getProfession() {
		return profession;
	}
	
	public void setProfession(String profession) {
		this.profession = profession;
	}
}
