package thymeleafexercise.residentevil.domain.models.bindings;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import thymeleafexercise.residentevil.annotation.validators.Creator;
import thymeleafexercise.residentevil.annotation.validators.RelealedOn;
import thymeleafexercise.residentevil.domain.entities.Capital;
import thymeleafexercise.residentevil.domain.entities.Magnitute;
import thymeleafexercise.residentevil.domain.entities.Mutation;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

public class VirusBindingModel {

    private String name;
    private String description;
    private String sideEffects;
    private String creator;
    private boolean isDeadly;
    private boolean isCurable;
    private Mutation mutation;

    private Integer turnover;
    private Integer hoursUntilTurn;
    private Magnitute magnitude;

    private LocalDate releasedOn;
    private List<Capital> capitals;


    @NotEmpty
    @Length(min = 3, max = 10)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotEmpty
    @Length(min = 5, max = 100)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Length(max = 50)
    public String getSideEffects() {
        return sideEffects;
    }

    public void setSideEffects(String sideEffects) {
        this.sideEffects = sideEffects;
    }

    @Creator
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }


    public boolean isDeadly() {
        return isDeadly;
    }

    public void setDeadly(boolean deadly) {
        isDeadly = deadly;
    }

    public boolean isCurable() {
        return isCurable;
    }

    public void setCurable(boolean curable) {
        isCurable = curable;
    }

    @NotNull
    public Mutation getMutation() {
        return mutation;
    }

    public void setMutation(Mutation mutation) {
        this.mutation = mutation;
    }

    @NotNull
    @Min(0)
    @Max(100)
    public Integer getTurnover() {
        return turnover;
    }

    public void setTurnover(Integer turnover) {
        this.turnover = turnover;
    }

    @NotNull
    @Min(1)
    @Max(12)
    public Integer getHoursUntilTurn() {
        return hoursUntilTurn;
    }

    public void setHoursUntilTurn(Integer hoursUntilTurn) {
        this.hoursUntilTurn = hoursUntilTurn;
    }

    @NotNull
    public Magnitute getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(Magnitute magnitude) {
        this.magnitude = magnitude;
    }

    @RelealedOn
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate getReleasedOn() {
        return releasedOn;
    }

    public void setReleasedOn(LocalDate releasedOn) {
        this.releasedOn = releasedOn;
    }

    @NotEmpty
    public List<Capital> getCapitals() {
        return capitals;
    }

    public void setCapitals(List<Capital> capitals) {
        this.capitals = capitals;
    }


}
