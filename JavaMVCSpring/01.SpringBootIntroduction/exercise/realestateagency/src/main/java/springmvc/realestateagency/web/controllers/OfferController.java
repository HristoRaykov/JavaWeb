package springmvc.realestateagency.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import springmvc.realestateagency.domain.models.binding.FindOfferBindingModel;
import springmvc.realestateagency.domain.models.binding.RegisterOfferBindingModel;
import springmvc.realestateagency.domain.models.service.FindOfferServiceModel;
import springmvc.realestateagency.domain.models.service.OfferServiceModel;
import springmvc.realestateagency.service.OfferService;

import javax.validation.Validator;
import java.util.Optional;

@Controller
public class OfferController {
	
	private final OfferService offerService;
	private final ModelMapper modelMapper;
	private final Validator validator;
	
	@Autowired
	public OfferController(OfferService offerService, ModelMapper modelMapper, Validator validator) {
		this.offerService = offerService;
		this.modelMapper = modelMapper;
		this.validator = validator;
	}
	
	@GetMapping("/register")
	public String register(){
		return "html/register.html";
	}
	
	@PostMapping("/register")
	public String registerConfirm(@ModelAttribute RegisterOfferBindingModel registerOfferBindingModel){
		OfferServiceModel offerServiceModel = this.modelMapper.map(registerOfferBindingModel,OfferServiceModel.class);
		
		Optional<OfferServiceModel> savedOfferOptional = this.offerService.save(offerServiceModel);
		
		if (savedOfferOptional.isEmpty()){
			return "redirect:/register";
		}
		
		return "redirect:/";
	}
	
	@GetMapping("/find")
	public String find(){
		return "html/find.html";
	}
	
	@PostMapping("/find")
	public String findConfirm(@ModelAttribute FindOfferBindingModel findOfferBindingModel){
		FindOfferServiceModel findOfferServiceModel =
				this.modelMapper.map(findOfferBindingModel,FindOfferServiceModel.class);
		
		if (!this.offerService.findApartment(findOfferServiceModel)){
			return "redirect:/find";
		}
		
		return "redirect:/";
	}
}
