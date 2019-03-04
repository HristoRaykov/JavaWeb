package springmvc.springessentials.exodia.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import springmvc.springessentials.exodia.domain.models.binding.UserLoginBindingModel;
import springmvc.springessentials.exodia.domain.models.binding.UserRegisterBindingModel;
import springmvc.springessentials.exodia.domain.models.services.DocumentServiceModel;
import springmvc.springessentials.exodia.domain.models.services.UserServiceModel;
import springmvc.springessentials.exodia.domain.models.views.HomePageDocumentViewModel;
import springmvc.springessentials.exodia.service.DocumentService;
import springmvc.springessentials.exodia.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class UserController {
	
	private final UserService userService;
	private final DocumentService documentService;
	private final ModelMapper modelMapper;
	
	@Autowired
	public UserController(UserService userService, DocumentService documentService, ModelMapper modelMapper) {
		this.userService = userService;
		this.documentService = documentService;
		this.modelMapper = modelMapper;
	}
	
	@GetMapping("/register")
	public ModelAndView register(Model model, HttpSession session) {
		if (session.getAttribute("username") != null) {
			return new ModelAndView("redirect:/home");
		}
		Map<String, Object> modelAsMap = model.asMap();
		
		if (modelAsMap.isEmpty()) {
			model.addAttribute("formData", new UserRegisterBindingModel());
			
			return new ModelAndView("/register", "formData", new UserRegisterBindingModel());
		}
		
		UserRegisterBindingModel userRegisterBindingModel = (UserRegisterBindingModel) modelAsMap.get("formData");
		BindingResult bindingResult = (BindingResult) modelAsMap.get("bindingResult");
		
		
		return new ModelAndView("register", "formData", userRegisterBindingModel);
		
	}
	
	@PostMapping("/register")
	public ModelAndView registerPost(@ModelAttribute() @Valid UserRegisterBindingModel userRegisterBindingModel, BindingResult bindingResult,
	                                 RedirectAttributes redirectAttributes, HttpSession session) {
		if (session.getAttribute("username") != null) {
			return new ModelAndView("redirect:/home");
		}
		
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("formData", userRegisterBindingModel);
			redirectAttributes.addFlashAttribute("bindingResult", bindingResult);
			
			return new ModelAndView("redirect:/register");
		}
		UserServiceModel userServiceModel = modelMapper.map(userRegisterBindingModel, UserServiceModel.class);
		if (userService.register(userServiceModel).isEmpty()) {
			redirectAttributes.addFlashAttribute("formData", userRegisterBindingModel);
			redirectAttributes.addFlashAttribute("bindingResult", bindingResult);
			return new ModelAndView("redirect:/register");
		}
		
		return new ModelAndView("redirect:/login");
	}
	
	@GetMapping("/login")
	public ModelAndView login(HttpSession session) {
		if (session.getAttribute("username") != null) {
			return new ModelAndView("redirect:/home");
		}
		
		return new ModelAndView("login");
		
	}
	
	@PostMapping("/login")
	public ModelAndView loginPost(@ModelAttribute UserLoginBindingModel userLoginBindingModel, HttpSession session) {
		if (session.getAttribute("username") != null) {
			return new ModelAndView("redirect:/home");
		}
		
		UserServiceModel userServiceModelOptional = modelMapper.map(userLoginBindingModel, UserServiceModel.class);
		if (userService.loginUser(userServiceModelOptional).isEmpty()) {
			return new ModelAndView("/login");
		}
		
		session.setAttribute("username", userServiceModelOptional.getUsername());
		session.setAttribute("userId", userServiceModelOptional.getId());
		
		return new ModelAndView("redirect:/home");
	}
	
	@GetMapping("/logout")
	public ModelAndView logout(HttpSession session) {
		if (session.getAttribute("username") == null) {
			return new ModelAndView("redirect:/login");
		}
		session.invalidate();
		
		return new ModelAndView("redirect:/");
	}
	
	@GetMapping("/home")
	public ModelAndView home(HttpSession session) {
		if (session.getAttribute("username") == null) {
			return new ModelAndView("redirect:/");
		}
		
		List<DocumentServiceModel> documentServiceModels = documentService.getAllDocuments();
		
		List<HomePageDocumentViewModel> homePageDocumentViewModelList = documentService.getAllDocuments().stream()
				.map(dsm ->  modelMapper.map(dsm, HomePageDocumentViewModel.class))
				.map(hpvm -> {
					if (hpvm.getTitle().length() > 12) {
						hpvm.setTitle(hpvm.getTitle().substring(0, 12) + "...");
						return hpvm;
					} else {
						return hpvm;
					}
				})
				.collect(Collectors.toList());
		
		
		return new ModelAndView("home", "documents", homePageDocumentViewModelList);
	}
	
}
