package springmvc.springessentials.exodia.domain.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity {

	private String id;
	
	@Id
	@GeneratedValue(generator = "uuid-string")
	@GenericGenerator(name = "uuid-string",strategy = "org.hibernate.id.UUIDGenerator")
	@Column(nullable = false, updatable = false, unique = true)
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
}
