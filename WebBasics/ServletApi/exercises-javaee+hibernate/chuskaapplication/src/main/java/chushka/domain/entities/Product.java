package chushka.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity(name = "products")
public class Product extends BaseEntity {

	private String name;
	private String description;
	private ProdType prodType;
	
	public Product() {
	}
	
	@Column(name = "name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "descrition")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	public ProdType getProdType() {
		return prodType;
	}
	public void setProdType(ProdType prodType) {
		this.prodType = prodType;
	}
}
