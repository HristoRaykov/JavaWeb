package thymeleafexercise.residentevil.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import thymeleafexercise.residentevil.domain.models.bindings.VirusBindingModel;
import thymeleafexercise.residentevil.domain.models.bindings.VirusEditBindingModel;
import thymeleafexercise.residentevil.domain.models.services.CapitalServiceModel;
import thymeleafexercise.residentevil.domain.models.services.VirusServiceModel;
import thymeleafexercise.residentevil.domain.models.views.AllVirusesViewModel;
import thymeleafexercise.residentevil.domain.models.views.CapitalListViewModel;
import thymeleafexercise.residentevil.service.CapitalService;
import thymeleafexercise.residentevil.service.VirusService;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/viruses")
public class VirusController {

    private final VirusService virusService;
    private final CapitalService capitalService;
    private final ModelMapper modelMapper;

    @Autowired
    public VirusController(VirusService virusService, CapitalService capitalService, ModelMapper modelMapper) {
        this.virusService = virusService;
        this.capitalService = capitalService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    public String addVirus(@ModelAttribute(name = "virus") VirusBindingModel virus, Model model) {
        List<CapitalListViewModel> capitals = getCapitalListViewModels();
        model.addAttribute("capitals", capitals);

        return "add-virus";
    }

    @PostMapping("/add")
    public String addVirusPost(@Valid @ModelAttribute(name = "virus") VirusBindingModel virus,
                               BindingResult bindingResult, Model model) {
        List<CapitalListViewModel> capitals = getCapitalListViewModels();
        model.addAttribute("capitals", capitals);

        if (bindingResult.hasErrors()) {
            return "add-virus";
        }

        VirusServiceModel virusServiceModel = modelMapper.map(virus, VirusServiceModel.class);

        Optional<VirusServiceModel> virusOptional = virusService.saveVirus(virusServiceModel);
        if (virusOptional.isEmpty()) {
            return "add-virus";
        }

        return "redirect:/viruses";
    }

    @GetMapping("")
    public String showAllViruses(Model model) {
        List<VirusServiceModel> virusServiceModels = virusService.extractAllViruses();
        List<AllVirusesViewModel> allViruses = Arrays.asList(modelMapper.map(virusServiceModels, AllVirusesViewModel[].class));

        model.addAttribute("viruses", allViruses);
        return "all-viruses";
    }

    @GetMapping("/{id}/edit")
    public String editVirus(@PathVariable(name = "id") String id,
                            @ModelAttribute(name = "virus") VirusEditBindingModel virus,
                            Model model) {
        List<CapitalListViewModel> capitals = getCapitalListViewModels();
        try {
            virus = modelMapper.map(virusService.extractById(id), VirusEditBindingModel.class);
        } catch (Exception e) {
            return "redirect:/viruses";
        }
        model.addAttribute("virus", virus);
        model.addAttribute("capitals", capitals);
        return "edit-virus";
    }

    @PostMapping("/{id}/edit")
    public String editVirusPost(@PathVariable(name = "id") String id,
                                @Valid @ModelAttribute(name = "virus") VirusEditBindingModel virus,
                                BindingResult bindingResult,
                                Model model) {
        List<CapitalListViewModel> capitals = getCapitalListViewModels();
        model.addAttribute("capitals",capitals);
        if (bindingResult.hasErrors()){
            return "edit-virus";
        }

        VirusServiceModel virusServiceModel = modelMapper.map(virus, VirusServiceModel.class);
        Optional<VirusServiceModel> editedVirusOptional = virusService.saveVirus(virusServiceModel);
        if (editedVirusOptional.isEmpty()) {
            return "redirect:/viruses/" + id + "/edit";
        }

        return "redirect:/viruses";
    }

    @GetMapping("/{id}/delete")
    public String deleteVirus(@PathVariable(name = "id") String id) {
        try {
            virusService.deleteVirusById(id);
        } catch (Exception ignored) {
        }

        return "redirect:/viruses";
    }

    @GetMapping("/show")
    public String showVirusesAndCapitals(){

        return "show";
    }

    @GetMapping(value = "/show/fetch",produces = "application/json")
    @ResponseBody
    public List<AllVirusesViewModel> showVirusesAndCapitalsFetch(){
        List<AllVirusesViewModel> viruses = Arrays.asList(modelMapper
                .map(virusService.extractAllViruses(),AllVirusesViewModel[].class));

        return viruses;
    }

    private List<CapitalListViewModel> getCapitalListViewModels() {
        List<CapitalServiceModel> capitalServiceModels = capitalService.extractAllCapitals();
        return Arrays.asList(modelMapper.map(capitalServiceModels, CapitalListViewModel[].class));
    }

}
