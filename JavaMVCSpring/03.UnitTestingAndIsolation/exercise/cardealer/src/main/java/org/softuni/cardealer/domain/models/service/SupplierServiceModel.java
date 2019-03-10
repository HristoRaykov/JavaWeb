package org.softuni.cardealer.domain.models.service;

public class SupplierServiceModel extends BaseServiceModel {

    private String name;
    private boolean isImporter;

    public SupplierServiceModel() {
    }

    public SupplierServiceModel(String name, boolean isImporter) {
        this.name = name;
        this.isImporter = isImporter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isImporter() {
        return isImporter;
    }

    public void setImporter(boolean importer) {
        isImporter = importer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SupplierServiceModel that = (SupplierServiceModel) o;

        if (isImporter() != that.isImporter()) return false;
        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        return getName() != null ? getName().equals(that.getName()) : that.getName() == null;
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getId() != null ? getId().hashCode() : 0);
        result = 31 * result + (isImporter() ? 1 : 0);
        return result;
    }
}
