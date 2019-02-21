package org.softuni.sboj.domain.entities;

public enum Sector {
	
	medicine,car,food,domestic,security;
	
	@Override
	public String toString() {
		return this.name().toLowerCase();
	}
}
