package springmvc.realestateagency.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springmvc.realestateagency.domain.models.service.OfferServiceModel;
import springmvc.realestateagency.domain.models.view.OfferViewModel;
import springmvc.realestateagency.service.OfferService;
import springmvc.realestateagency.util.HtmlFileReader;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Controller
public class HomeController {
	
	private final OfferService offerService;
	private final HtmlFileReader htmlFileReader;
	private final ModelMapper modelMapper;
	
	@Autowired
	public HomeController(OfferService offerService, HtmlFileReader htmlFileReader, ModelMapper modelMapper) {
		this.offerService = offerService;
		this.htmlFileReader = htmlFileReader;
		this.modelMapper = modelMapper;
	}
	
	
	@GetMapping("/")
	@ResponseBody
	public String index() throws IOException {
		List<OfferServiceModel> offerServiceModels = this.offerService.getAllOffers();
		List<OfferViewModel> offerViewModels = Arrays.asList(this.modelMapper.map(offerServiceModels,OfferViewModel[].class));
		String htmlContent = htmlFileReader.readHtmlFromFile("index");
		if (offerServiceModels.size()==0){
			return htmlContent.replace("{{offers}}","There aren't any offers");
		}
		String offersHtml = this.buildOffersHtml(offerViewModels);
		htmlContent = htmlContent.replace("{{offers}}",offersHtml);
		
		return htmlContent;
	}
	
	private String buildOffersHtml(List<OfferViewModel> offerViewModels) {
		StringBuilder offersHtml = new StringBuilder();
		
		for (OfferViewModel offerViewModel : offerViewModels) {
			offersHtml.append("<div class=\"apartment\">").append(System.lineSeparator())
					.append("<p>Rent: ").append(offerViewModel.getApartmentRent()).append("</p>").append(System.lineSeparator())
					.append("<p>Type: ").append(offerViewModel.getApartmentType()).append("</p>").append(System.lineSeparator())
					.append("<p>Commission: ").append(offerViewModel.getAgencyCommission()).append("</p>").append(System.lineSeparator())
					.append("</div>").append(System.lineSeparator());
		}
		return offersHtml.toString();
	}
	

}
