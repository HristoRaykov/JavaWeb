package thymeleafexercise.residentevil.service;

import thymeleafexercise.residentevil.domain.models.services.CapitalServiceModel;

import java.util.List;

public interface CapitalService {
    List<CapitalServiceModel> extractAllCapitals();
}
