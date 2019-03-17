package thymeleafexercise.residentevil.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import thymeleafexercise.residentevil.domain.entities.Virus;
import thymeleafexercise.residentevil.domain.models.services.VirusServiceModel;
import thymeleafexercise.residentevil.repository.VirusRepository;

import javax.validation.Validator;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class VirusServiceImpl implements VirusService {

    private final VirusRepository virusRepository;
    private final ModelMapper modelMapper;
    private final Validator validator;

    public VirusServiceImpl(VirusRepository virusRepository, ModelMapper modelMapper, Validator validator) {
        this.virusRepository = virusRepository;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }


    @Override
    public Optional<VirusServiceModel> saveVirus(VirusServiceModel virusServiceModel) {
        if(validator.validate(virusServiceModel).size()>0){
            return Optional.empty();
        }

        Virus virus = modelMapper.map(virusServiceModel, Virus.class);

        try {
            virus = virusRepository.save(virus);
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.of(modelMapper.map(virus,VirusServiceModel.class));
    }

    @Override
    public List<VirusServiceModel> extractAllViruses() {
        return Arrays.asList(modelMapper.map(virusRepository.findAll(),VirusServiceModel[].class));
    }

    @Override
    public void deleteVirusById(String id) {
        virusRepository.deleteById(id);
    }

    @Override
    public VirusServiceModel extractById(String id) {
        Optional<Virus> virus = virusRepository.findById(id);
        return modelMapper.map(virus.get(),VirusServiceModel.class);
    }
}
