package thymeleafexercise.residentevil.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import thymeleafexercise.residentevil.domain.models.services.CapitalServiceModel;
import thymeleafexercise.residentevil.domain.models.views.CapitalTableViewModel;
import thymeleafexercise.residentevil.service.CapitalService;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/capitals")
public class CapitalController {

    private final CapitalService capitalService;
    private final ModelMapper modelMapper;

    public CapitalController(CapitalService capitalService, ModelMapper modelMapper) {
        this.capitalService = capitalService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "/show/fetch",produces = "application/json")
    @ResponseBody
    public List<CapitalTableViewModel> shawCapitals(){
        List<CapitalServiceModel> capitalServiceModels = capitalService.extractAllCapitals();
        List<CapitalTableViewModel> capitals = Arrays.asList(modelMapper
                .map(capitalServiceModels,CapitalTableViewModel[].class));
        return capitals;
    }

}
