package thymeleafexercise.residentevil.service;

import thymeleafexercise.residentevil.domain.models.services.VirusServiceModel;

import java.util.List;
import java.util.Optional;

public interface VirusService {
    Optional<VirusServiceModel> saveVirus(VirusServiceModel virusServiceModel);

    List<VirusServiceModel> extractAllViruses();

    void deleteVirusById(String id);

    VirusServiceModel extractById(String id);
}
