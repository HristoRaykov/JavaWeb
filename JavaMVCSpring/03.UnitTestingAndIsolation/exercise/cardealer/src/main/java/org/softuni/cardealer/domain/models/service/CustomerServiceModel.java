package org.softuni.cardealer.domain.models.service;

import java.time.LocalDate;

public class CustomerServiceModel extends BaseServiceModel {

    private String name;
    private LocalDate birthDate;
    private boolean isYoungDriver;

    public CustomerServiceModel() {
    }

    public CustomerServiceModel(String name, LocalDate birthDate, boolean isYoungDriver) {
        this.name = name;
        this.birthDate = birthDate;
        this.isYoungDriver = isYoungDriver;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isYoungDriver() {
        return isYoungDriver;
    }

    public void setYoungDriver(boolean youngDriver) {
        isYoungDriver = youngDriver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomerServiceModel that = (CustomerServiceModel) o;

        if (isYoungDriver() != that.isYoungDriver()) return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        return getBirthDate() != null ? getBirthDate().equals(that.getBirthDate()) : that.getBirthDate() == null;
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getBirthDate() != null ? getBirthDate().hashCode() : 0);
        result = 31 * result + (getId() != null ? getId().hashCode() : 0);
        result = 31 * result + (isYoungDriver() ? 1 : 0);
        return result;
    }
}
