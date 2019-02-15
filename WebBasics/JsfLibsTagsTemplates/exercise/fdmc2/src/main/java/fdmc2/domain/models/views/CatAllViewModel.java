package fdmc2.domain.models.views;

import java.math.BigDecimal;
import java.util.Date;

public class CatAllViewModel {
	
	private String name;
	private String breed;
	private String color;
	private String gender;
	private BigDecimal price;
	private Date addedOn;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getBreed() {
		return breed;
	}
	
	public void setBreed(String breed) {
		this.breed = breed;
	}
	
	public String getColor() {
		return color;
	}
	
	public void setColor(String color) {
		this.color = color;
	}
	

	
	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public BigDecimal getPrice() {
		return price;
	}
	
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public Date getAddedOn() {
		return addedOn;
	}
	
	public void setAddedOn(Date addedOn) {
		this.addedOn = addedOn;
	}
}
