package metube.domain.models.view.alltubesviewmodels;

public class TubeViewModel {
	
	private String id;
	private String title;
	private String author;
	private String youTubeId;
	
	
	public TubeViewModel() {
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getYouTubeId() {
		return youTubeId;
	}
	
	public void setYouTubeId(String youTubeId) {
		this.youTubeId = youTubeId;
	}
}
