package thymeleafexercise.residentevil.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import thymeleafexercise.residentevil.domain.entities.Capital;
import thymeleafexercise.residentevil.domain.models.services.CapitalServiceModel;
import thymeleafexercise.residentevil.repository.CapitalRepository;

import java.util.Arrays;
import java.util.List;

@Service
public class CapitalServiceImpl implements CapitalService {

    private final CapitalRepository capitalRepository;
    private final ModelMapper modelMapper;

    public CapitalServiceImpl(CapitalRepository capitalRepository, ModelMapper modelMapper) {
        this.capitalRepository = capitalRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CapitalServiceModel> extractAllCapitals() {
        List<Capital> capitals = capitalRepository.findAll();
        return Arrays.asList(modelMapper.map(capitals,CapitalServiceModel[].class));
    }
}
