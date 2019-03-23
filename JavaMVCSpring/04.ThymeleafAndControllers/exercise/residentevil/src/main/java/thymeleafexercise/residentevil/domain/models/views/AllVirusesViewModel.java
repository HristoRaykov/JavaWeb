package thymeleafexercise.residentevil.domain.models.views;

import thymeleafexercise.residentevil.domain.entities.Magnitute;

import java.time.LocalDate;

public class AllVirusesViewModel {

    private String id;
    private String name;
    private Magnitute magnitude;
    private LocalDate releasedOn;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Magnitute getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(Magnitute magnitude) {
        this.magnitude = magnitude;
    }

    public LocalDate getReleasedOn() {
        return releasedOn;
    }

    public void setReleasedOn(LocalDate releasedOn) {
        this.releasedOn = releasedOn;
    }
}
