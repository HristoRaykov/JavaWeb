package fdmc2.web.jsfbeans;

import fdmc2.domain.models.binding.CatCreateBindingModel;
import fdmc2.domain.models.services.CatServiceModel;
import fdmc2.service.CatService;
import org.modelmapper.ModelMapper;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
public class CatCreateBean {
	
	private CatCreateBindingModel catCreateBindingModel;

	private final CatService catService;
	private final ModelMapper modelMapper;
	
	@Inject
	public CatCreateBean(CatService catService, ModelMapper modelMapper) {
		this.catService = catService;
		this.modelMapper = modelMapper;
		this.catCreateBindingModel = new CatCreateBindingModel();
	}
	
	public CatCreateBindingModel getCatCreateBindingModel() {
		return catCreateBindingModel;
	}
	
	
	public void registerCat() throws IOException {
		CatServiceModel catServiceModel = this.modelMapper.map(catCreateBindingModel,CatServiceModel.class);
		this.catService.save(catServiceModel);
		FacesContext.getCurrentInstance().getExternalContext().redirect("/jsf/all-cats.xhtml");
	}
}
