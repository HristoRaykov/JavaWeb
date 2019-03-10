package org.softuni.cardealer.domain.models.service;

import java.util.List;

public class CarServiceModel extends BaseServiceModel {

    private String make;
    private String model;
    private Long travelledDistance;
    private List<PartServiceModel> parts;

    public CarServiceModel() {
    }

    public CarServiceModel(String make, String model, Long travelledDistance, List<PartServiceModel> parts) {
        this.make = make;
        this.model = model;
        this.travelledDistance = travelledDistance;
        this.parts = parts;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getTravelledDistance() {
        return travelledDistance;
    }

    public void setTravelledDistance(Long travelledDistance) {
        this.travelledDistance = travelledDistance;
    }

    public List<PartServiceModel> getParts() {
        return parts;
    }

    public void setParts(List<PartServiceModel> parts) {
        this.parts = parts;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CarServiceModel that = (CarServiceModel) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getMake() != null ? !getMake().equals(that.getMake()) : that.getMake() != null) return false;
        if (getModel() != null ? !getModel().equals(that.getModel()) : that.getModel() != null) return false;
        return getTravelledDistance() != null ? getTravelledDistance().equals(that.getTravelledDistance()) : that.getTravelledDistance() == null;
    }

    @Override
    public int hashCode() {
        int result = getMake() != null ? getMake().hashCode() : 0;
        result = 31 * result + (getModel() != null ? getModel().hashCode() : 0);
        result = 31 * result + (getId() != null ? getId().hashCode() : 0);
        result = 31 * result + (getTravelledDistance() != null ? getTravelledDistance().hashCode() : 0);
        return result;
    }
}
