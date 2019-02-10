package metube.domain.models.view.alltubesviewmodels;

import java.util.ArrayList;
import java.util.List;

public class AllTubesViewModel {
	
	private String username;
	
	private List<TubeViewModel> tubeViewModels;
	
	public AllTubesViewModel() {
		tubeViewModels = new ArrayList<>();
	}
	
	public AllTubesViewModel(String username, List<TubeViewModel> tubeViewModels) {
		this();
		this.username = username;
		this.tubeViewModels = tubeViewModels;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public List<TubeViewModel> getTubeViewModels() {
		return tubeViewModels;
	}
	
	public void setTubeViewModels(List<TubeViewModel> tubeViewModels) {
		this.tubeViewModels = tubeViewModels;
	}
}
