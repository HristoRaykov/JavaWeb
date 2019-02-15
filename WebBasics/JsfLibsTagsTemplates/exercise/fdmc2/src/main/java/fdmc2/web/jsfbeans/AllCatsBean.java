package fdmc2.web.jsfbeans;


import fdmc2.domain.models.services.CatServiceModel;
import fdmc2.domain.models.views.CatAllViewModel;
import fdmc2.service.CatService;
import org.modelmapper.ModelMapper;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Named
public class AllCatsBean {
	
	private List<CatAllViewModel> allCats;
	
	private String orderBy;
	
	private final CatService catService;
	private final ModelMapper modelMapper;
	
	@Inject
	public AllCatsBean(CatService catService, ModelMapper modelMapper) {
		this.catService = catService;
		this.modelMapper = modelMapper;
		this.initOrderBy();
		this.initAllCats();
	}
	
	private void initOrderBy() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		orderBy = (String) session.getAttribute("orderByColumnName");
		session.setAttribute("orderByColumnName",null);
	}
	
	private void initAllCats() {
		if (orderBy==null) {
			List<CatServiceModel> catServiceModels = this.catService.getAllCats();
			allCats = Arrays.asList(this.modelMapper.map(catServiceModels, CatAllViewModel[].class));
		}else{
			List<CatServiceModel> catServiceModels = this.catService.getAllCatsOrderedBy(orderBy);
			allCats = Arrays.asList(this.modelMapper.map(catServiceModels, CatAllViewModel[].class));
		}
	}
	
	public List<CatAllViewModel> getAllCats() {
		return allCats;
	}
	
	public void setAllCats(List<CatAllViewModel> allCats) {
		this.allCats = allCats;
	}
	
	public String getOrderBy() {
		return orderBy;
	}
	
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	

}
