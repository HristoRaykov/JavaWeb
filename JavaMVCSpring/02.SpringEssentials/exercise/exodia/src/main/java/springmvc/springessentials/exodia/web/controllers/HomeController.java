package springmvc.springessentials.exodia.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
	
	@GetMapping("/")
	public ModelAndView home(HttpSession session){
		if (session.getAttribute("username")!=null){
			return new ModelAndView("redirect:/home");
		}
		
		return new ModelAndView("/index");
	}
	
	
}
