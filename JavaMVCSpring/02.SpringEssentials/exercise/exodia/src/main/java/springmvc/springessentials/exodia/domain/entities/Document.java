package springmvc.springessentials.exodia.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "documents")
public class Document extends BaseEntity{
	
	private String title;
	private String content;
	
	public Document() {
	}
	
	@Column
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(columnDefinition = "text")
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
}
